document.addEventListener("DOMContentLoaded", () => {
    const contentBox = document.getElementById("boardContentPageBoardText");
    const hiddenContent = document.getElementById("hiddenContent");

    if (contentBox && contentBox.children.length === 0) {
        contentBox.innerHTML = hiddenContent.value;
    }

})