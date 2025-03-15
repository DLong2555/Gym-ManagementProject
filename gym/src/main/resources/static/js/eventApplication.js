document.addEventListener("DOMContentLoaded", () => {
    const children = document.querySelectorAll('.payGymCheckBox');
    const price = document.querySelector(".price").value.replace(/,/g, '');
    const totalPrice = document.getElementById("payGymAddResult");
    const paymentBtn = document.getElementById("payGymPayA");
    let checkedChild = 0;


    updateTotalPrice();

    children.forEach(child => {
        child.addEventListener("change", (e) => {
            updateTotalPrice();
        })
    })

    function updateTotalPrice() {
        checkedChild = 0;
        children.forEach(child => {
            if(child.checked) {
                checkedChild++;
            }
        });

        let formatTotalPrice = (parseInt(price) * checkedChild).toLocaleString('ko-KR') + "원";
        totalPrice.textContent = "총 가격 : " + formatTotalPrice;
        paymentBtn.textContent = formatTotalPrice + " 결제하기"
    }
})