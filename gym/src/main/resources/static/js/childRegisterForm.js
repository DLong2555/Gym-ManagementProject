document.addEventListener('DOMContentLoaded', () => {

    const id = document.getElementById('id').value;
    const customerName = document.getElementById('customer').value;
    const price = document.getElementById('gymPrice').value.replace(/,/g, '');
    const memPhone = document.getElementById('memPhone').value;
    const orderId = crypto.randomUUID();
    const orderName = document.getElementById('orderName').value;
    const childName = document.getElementById('name').value;

    async function savePayInfo() {
        const requestData = {
            orderId: orderId,
            payAmount: price
        }

        const response = await fetch("/pay/saveAmount", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(requestData)
        });
    }
    savePayInfo();
    // ------  SDK 초기화 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
    const clientKey = "test_ck_jkYG57Eba3GAJo7o5RErpWDOxmA1";
    const customerKey = crypto.randomUUID();
    const tossPayments = TossPayments(clientKey);
    // 회원 결제
    // @docs https://docs.tosspayments.com/sdk/v2/js#tosspaymentspayment
    const payment = tossPayments.payment({customerKey});

    // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#paymentrequestpayment

    async function verifyRequest(){
        const requestData = {
            orderId: orderId,
            payAmount: price
        }

        try{
            const response = await fetch("/pay/verifyAmount", {
                method: 'POST',
                headers: {"content-type": "application/json"},
                body: JSON.stringify(requestData)
            });

            if(!response.ok){
                const errorData = await response.json();
                location.href = window.location.origin + `/fail?message=${errorData.message}&code=${errorData.code}`;
            }

            console.log("결제 금액 검증 성공, 결제 진행");
            await requestPayment();
        }catch(error){

        }
    }

    async function requestPayment() {
        // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
        // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
        await payment.requestPayment({
            method: "CARD", // 카드 결제
            amount: {
                currency: "KRW",
                value: parseInt(price),
            },
            orderId: orderId, // 고유 주문번호
            orderName: orderName,
            successUrl: window.location.origin + `/success`, // 결제 요청이 성공하면 리다이렉트되는 URL
            failUrl: window.location.origin + "/fail", // 결제 요청이 실패하면 리다이렉트되는 URL
            customerName: customerName,
            customerMobilePhone: memPhone,
            // 카드 결제에 필요한 정보
            card: {
                useEscrow: false,
                flowMode: "DEFAULT", // 통합결제창 여는 옵션
                useCardPoint: false,
                useAppCardOnly: false,
            },
        });

    }

    document.getElementById('registerForm').addEventListener('submit',(e) => {
        e.preventDefault();

        let form = document.getElementById("registerForm");
        let formData = new FormData(form);
        formData.append("orderId", orderId);

        let request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                if (request.status === 200) {

                    if (request.response.success === "success") {
                        verifyRequest();

                    }else{
                        let result = request.response;

                        document.querySelectorAll(".field-error").forEach(field => {
                            field.textContent = "";
                        })

                        Object.keys(result).forEach((key) => {
                            let field = document.getElementById(key);
                            console.log(field);
                            if(field){
                                let errorField = field.closest('.joinInputDiv').querySelector('.field-error');

                                if (errorField) {
                                    errorField.textContent = "X " + result[key];
                                }
                            }
                        })

                        return false;
                    }
                }
            }
        }

        request.responseType = "json";
        request.open("POST", "/gym/child/check", true);
        request.send(formData);
    })


})