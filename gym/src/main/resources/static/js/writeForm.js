document.addEventListener("DOMContentLoaded", function () {

    const imageDiv = document.getElementById("boardWritePageImageInputButton");
    const fileInput = document.getElementById("fileInput");

    imageDiv.addEventListener("click", function () {
        fileInput.click();
    })

    fileInput.addEventListener("change", async (e) => {

        let files = e.target.files;
        let formData = new FormData();
        for (let i = 0; i < files.length; i++) {
            formData.append("files", files[i]);
        }

        const response = await fetch("/gym/board/upload", {
            method: "POST",
            body: formData,
        });

        if(!response.ok) {

        }

        const uploads = await response.json();
        const boardWritePageImageBox = document.getElementById("boardWritePageImageBox");

        uploads.forEach((upload) => {
            const imageBoxEach = document.createElement("div");
            imageBoxEach.classList.add("imageBox");

            const img = document.createElement("img");
            img.src = `/gym/board/images/${upload.storeFileName}`;
            img.width = 280;
            img.height = 280;
            img.classList.add("uploadImage");

            const cancelBtn = document.createElement("button");
            cancelBtn.classList.add("cancel");
            cancelBtn.textContent = "X";

            boardWritePageImageBox.appendChild(imageBoxEach);
            imageBoxEach.appendChild(img);
            imageBoxEach.appendChild(cancelBtn);
        });

    });

    const contentBox = document.getElementById("boardWritePageBoardText");
    const boardWritePageImageBox = document.getElementById("boardWritePageImageBox");

    boardWritePageImageBox.addEventListener("click", (e) => {
        if (e.target.classList.contains("uploadImage")) {
            const img = e.target.cloneNode(true);
            img.style.width = "auto";
            img.style.height = "auto";
            contentBox.appendChild(img);
        }

        if (e.target.classList.contains("cancel")) {
            const imageBox = e.target.parentElement;
            const imgSrc = imageBox.querySelector(".uploadImage").src;

            imageBox.remove();

            contentBox.querySelectorAll("img").forEach(img => {
                if (img.src === imgSrc) img.remove();
            })
        }
    });

    // const cancelBtns = document.querySelectorAll(".cancel");
    // cancelBtns.forEach(btn=> {
    //     btn.addEventListener("click", (e) => {
    //         e.preventDefault();
    //
    //         const imageBox = btn.parentElement;
    //         const imgSrc = imageBox.querySelector(".uploadImage").src;
    //
    //         imageBox.remove();
    //
    //         contentBox.querySelectorAll("img").forEach(img => {
    //             if (img.src === imgSrc) img.remove();
    //         })
    //
    //     })
    // })

})
