document.addEventListener('DOMContentLoaded', () => {

    const id = document.getElementById('id').value;
    const name = document.getElementById('name').value;
    const price = document.getElementById('gymPrice').value.replace(/,/g, '');
    const memPhone = document.getElementById('memPhone').value;

    function showPaymentPopup() {
        // 결제 UI 렌더링 및 결제 버튼 표시
        main();

        async function main() {
            const button = document.getElementById("payment-request-button");

            // ------  결제위젯 초기화 ------
            const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
            const tossPayments = TossPayments(clientKey);

            // 회원 결제
            const customerKey = "W4mJFqy88gfLBCtRDdH9D";
            const widgets = tossPayments.widgets({
                customerKey,
            });

            // ------ 주문의 결제 금액 설정 ------
            await widgets.setAmount({
                currency: "KRW",
                value: parseInt(price),
            });

            await Promise.all([
                // ------  결제 UI 렌더링 ------
                widgets.renderPaymentMethods({
                    selector: "#payment-method",
                    variantKey: "DEFAULT",
                }),
                // ------  이용약관 UI 렌더링 ------
                widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" }),
            ]);

            // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
            button.addEventListener("click", async function () {
                const requestData = {
                    orderId: "uvL5eGipPgi5pRxZw7f53",
                    payAmount: price
                }

                const response = await fetch("/pay/saveAmount", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(requestData)
                });

                await widgets.requestPayment({
                    orderId: "uvL5eGipPgi5pRxZw7f53",
                    orderName: "토스 티셔츠 외 2건",
                    successUrl: window.location.origin + "/success",
                    failUrl: window.location.origin + "/fail",
                    customerName: name,
                    customerMobilePhone: memPhone
                });
            });
        }

        document.getElementById("payment-popup").style.display = "flex";
    }

    // 팝업 닫기
    // document.getElementById("close-popup").addEventListener("click", function () {
    //     document.getElementById("payment-popup").style.display = "none";
    // });


    document.getElementById('registerForm').addEventListener('submit', event => {
        event.preventDefault();
        showPaymentPopup();
        const gymId = document.getElementById("gymId").value;
        const gymName = document.getElementById('gymName').value;

        let form = document.getElementById("registerForm");
        let formData = new FormData(form);



        // let request = new XMLHttpRequest();
        // request.onreadystatechange = function () {
        //     if (request.readyState === 4) {
        //         if (request.status === 200) {
        //
        //             if (request.response.success === "success") {
        //                 console.log("response: " + request.response);
        //
        //                 if(confirm("등록 성공! 추가 등록하시겠습니까?")){
        //                     const form = document.createElement("form");
        //                     form.method = "POST";
        //                     form.action = "/gym/child/form";
        //
        //                     const gymIdInput = document.createElement("input");
        //                     gymIdInput.type = "hidden";
        //                     gymIdInput.name = "gymId";
        //                     gymIdInput.value = gymId;
        //
        //                     const gymNameInput = document.createElement("input");
        //                     gymNameInput.type = "hidden";
        //                     gymNameInput.name = "gymName";
        //                     gymNameInput.value = gymName;
        //
        //                     form.appendChild(gymIdInput);
        //                     form.appendChild(gymNameInput);
        //                     document.body.appendChild(form);
        //
        //                     form.submit();
        //                 }else{
        //                     window.location.href="/";
        //                 }
        //             }else{
        //                 let result = request.response;
        //                 console.log(result);
        //
        //                 document.querySelectorAll(".field-error").forEach(field => {
        //                     field.textContent = "";
        //                 })
        //
        //                 Object.keys(result).forEach((key) => {
        //                     let field = document.getElementById(key);
        //
        //                     if(field){
        //                         let errorField = field.closest('.joinInputDiv').querySelector('.field-error');
        //
        //                         if (errorField) {
        //                             errorField.textContent = "X " + result[key];
        //                         }
        //                     }
        //                 })
        //
        //                 return false;
        //             }
        //         }
        //     }
        // }

        // request.responseType = "json";
        // request.open("POST", "/gym/child/new", true);
        // request.send(formData);
    })


})