document.addEventListener("DOMContentLoaded", function() {
    const btn = document.getElementsByTagName("button");

    for(let item of btn) {
        item.addEventListener("mouseover", function() {
            item.style.backgroundColor = "#333333";
            item.style.color = "whitesmoke";
        })

        item.addEventListener("mouseout", function() {
            item.style.backgroundColor = "black";
            item.style.color = "white";
        })
    }
})