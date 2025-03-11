document.addEventListener("DOMContentLoaded", () => {
    const url = new URL(window.location.href);

    const totalPages = document.getElementById("totalPages").value - 1;
    const currentPage =parseInt(document.getElementById("currentPage").value);

    if(totalPages > 0) {
        const leftNext = document.querySelector(".leftNext");
        const rightNext = document.querySelector(".rightNext");
        const endMove = document.querySelector(".endMove");
        const startMove = document.querySelector(".startMove");

        rightNext.addEventListener("click", function () {
            if (currentPage < totalPages) {
                url.searchParams.set("page", currentPage + 1);
                location.href = url.href;
            }
        });

        leftNext.addEventListener("click", function () {
            if (currentPage > 0) {
                url.searchParams.set("page", currentPage - 1);
                location.href = url.href;
            }
        })

        startMove.addEventListener("click", function () {
            if (currentPage < totalPages) {
                url.searchParams.set("page", totalPages);
                location.href = url.href;
            }
        })

        endMove.addEventListener("click", function () {
            if (currentPage > 0) {
                url.searchParams.set("page", 0);
                location.href = url.href;
            }
        })
    }

    document.querySelector('.boardPageWriteButton').querySelector("[name='gymName']").value = document.querySelector(".selected").textContent;

})