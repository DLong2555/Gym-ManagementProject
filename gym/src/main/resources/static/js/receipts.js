document.addEventListener("DOMContentLoaded", () => {

    const cancelBtns = document.querySelectorAll(".cancelButton");

    cancelBtns.forEach(cancelBtn => {
        cancelBtn.addEventListener("click", (e) => {
            e.preventDefault();

            const receiptContents = e.target.closest('.receiptContents');

            const formData = {
                id: receiptContents.querySelector('.id').value,
                paymentKey: receiptContents.querySelector('.paymentKey').value,
                orderName: receiptContents.querySelector('.orderName').value,
                childName: receiptContents.querySelector(".childName").value
            }

            console.log(formData);

            cancel(formData, receiptContents);
        })
    })

    async function cancel(formData, receiptContents) {

        const response = await fetch("/gym/payment/cancel", {
            method: "POST",
            headers: {"content-type": "application/json"},
            body: JSON.stringify(formData)
        });


        if(!response.ok){

        }

        receiptContents.querySelector('.payStatus').textContent = "CANCELLED";
        let btn = receiptContents.querySelector('.cancelButton');
        btn.disabled = true;
        btn.style.backgroundColor = "#ccc";
        btn.style.cursor = "not-allowed";

    }

})