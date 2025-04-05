document.addEventListener("DOMContentLoaded", () => {
    const children = document.querySelectorAll('.payGymCheckBox');
    const totalPrice = document.getElementById("payGymAddResult");
    const paymentBtn = document.getElementById("payGymPayA");
    let totalPayPrice = 0;

    updateTotalPrice();

    children.forEach(child => {
        child.addEventListener("change", (e) => {
            updateTotalPrice();
        })
    })

    function updateTotalPrice() {
        totalPayPrice = 0;
        children.forEach(child => {
            const price = document.querySelector(".costPayGymPrice").value.replace(/,/g, '').replace('원', '').trim();
            if(child.checked) {
                totalPayPrice += parseInt(price);
            }
        });

        let formatTotalPrice = totalPayPrice.toLocaleString('ko-KR') + "원";
        totalPrice.textContent = "총 가격 : " + formatTotalPrice;
        paymentBtn.textContent = formatTotalPrice + " 결제하기"
    }

    const orderId = crypto.randomUUID() + "-regular";

    async function savePayInfo() {
        const requestData = {
            orderId: orderId,
            payAmount: totalPayPrice
        }

        const response = await fetch("/pay/saveAmount", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(requestData)
        });
    }

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
            payAmount: totalPayPrice
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

    const orderName = document.getElementById('payGymTitle').textContent;
    const customer = document.getElementById('memName').value;
    const phone = document.getElementById('phoneNumber').value;

    async function requestPayment() {
        // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
        // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
        await payment.requestPayment({
            method: "CARD", // 카드 결제
            amount: {
                currency: "KRW",
                value: parseInt(totalPayPrice),
            },
            orderId: orderId, // 고유 주문번호
            orderName: orderName,
            successUrl: window.location.origin + `/success`, // 결제 요청이 성공하면 리다이렉트되는 URL
            failUrl: window.location.origin + "/fail", // 결제 요청이 실패하면 리다이렉트되는 URL
            customerName: customer,
            customerMobilePhone: phone,
            // 카드 결제에 필요한 정보
            card: {
                useEscrow: false,
                flowMode: "DEFAULT", // 통합결제창 여는 옵션
                useCardPoint: false,
                useAppCardOnly: false,
            },
        });
    }

    paymentBtn.addEventListener("click", async function(e){
        e.preventDefault();
        savePayInfo();

        let selectedChild = [];
        children.forEach(child => {
            if(child.checked){
                selectedChild.push(child.closest('.payGymUserEachTitle').querySelector('.payChildNo').value);
            }
        })

        let formData = {
            title: document.querySelector('.title').value,
            childIds: selectedChild,
            orderId: orderId
        }

        const response = await fetch("/gym/child/pay/check", {
            method: "POST",
            headers: {"content-type": "application/json"},
            body: JSON.stringify(formData)
        });

        verifyRequest();
    })

})