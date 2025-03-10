document.addEventListener("DOMContentLoaded", () => {
    const url = window.location.pathname.split("/");
    const gymId = url[url.length - 1];
    const gymNames = document.querySelectorAll('.gymName');

    console.log(gymId);

    gymNames.forEach((gymName) => {
        console.log(gymName.dataset.id);
        if(gymName.dataset.id === gymId) {
            console.log(gymName);
            gymName.classList.add("selected");
        }
    })

})