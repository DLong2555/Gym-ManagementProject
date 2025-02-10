document.addEventListener('DOMContentLoaded', () => {
    const btn = document.querySelectorAll('.chooseMUButton');

    btn.forEach(btn => {
        const btnEl = btn.querySelector('a');

        btn.addEventListener('mouseover', function () {
            btnEl.style.backgroundColor = "#333333";
            btnEl.style.color = "whitesmoke";
        });

        btn.addEventListener('mouseout', function () {
            btnEl.style.backgroundColor = "black";
            btnEl.style.color = "white";
        });
    });
});