document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const paymentKey = urlParams.get("paymentKey");
    const orderId = urlParams.get("orderId");
    const amount = urlParams.get("amount");

    async function confirm() {
        const requestData = {
            paymentKey: paymentKey,
            orderId: orderId,
            amount: amount,
        };

        const response = await fetch("/confirm", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestData),
        });

        if (!response.ok) {
            const json = await response.json();
            // 결제 실패 비즈니스 로직을 구현하세요.
            console.log(json);
            window.location.href = `/fail?message=${json.message}&code=${json.code}`;
            return false;
        }

    }
    confirm();

    const paymentKeyElement = document.getElementById("paymentKey");
    const orderIdElement = document.getElementById("orderId");
    const amountElement = document.getElementById("amount");

    paymentKeyElement.textContent = paymentKey;
    orderIdElement.textContent = orderId;
    amountElement.textContent = `${amount}원`;
})
