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
    const hiddenContent = document.getElementById("hiddenContent");

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

    const observer = new MutationObserver((() => {
        hiddenContent.value = contentBox.innerHTML;
    }));

    observer.observe(contentBox, { childList: true, subtree: true });

    const placeholder = document.querySelector(".boardWritePageSelectPlaceholder");
    const subSelect = document.querySelector(".boardWritePageSubSelect");
    const subSelectEach = document.querySelectorAll(".boardWritePageSubSelectEach");
    const hiddenCategoryInput = document.getElementById("boardCategory");

    if (!hiddenCategoryInput.value) {
        hiddenCategoryInput.value = "ANNOUNCEMENT";  // 기본값 설정
        // placeholder.innerHTML = '공지 <div class="boardWritePageDown"></div>';  // UI 업데이트
    }

    // 드롭다운 열고 닫기
    placeholder.addEventListener("click", function () {
        if (subSelect.style.display === "flex") {
            subSelect.style.display = "none";
        } else {
            subSelect.style.display = "flex";
        }
    });

    const eventInputBox = document.getElementById("boardEventInputBox");
    // 항목 선택 시 Placeholder 변경 및 드롭다운 닫기
    subSelectEach.forEach(option => {
        option.addEventListener("click", function () {
            const selectedValue = option.textContent.trim();
            placeholder.innerHTML = selectedValue + ' <div class="boardWritePageDown"></div>'; // UI 업데이트
            hiddenCategoryInput.value = option.getAttribute("data-value"); // hidden input 값 설정
            subSelect.style.display = "none"; // 드롭다운 닫기

            console.log(option.textContent);
            if(option.textContent === "활동"){
                eventInputBox.style.display = "flex";
            }else{
                eventInputBox.style.display = "none";
            }
        });

        // 마우스 호버 효과 추가
        option.addEventListener("mouseenter", function () {
            option.style.backgroundColor = "#f3f3f3";
            option.style.color = "gray";
        });

        option.addEventListener("mouseleave", function () {
            option.style.backgroundColor = "white";
            option.style.color = "#424949";
        });
    });

    // 드롭다운 외부 클릭 시 닫기
    document.addEventListener("click", function (event) {
        if (!event.target.closest(".boardWritePageSelectPlaceholder") &&
            !event.target.closest(".boardWritePageSubSelect")) {
            subSelect.style.display = "none";
        }
    });

    // Placeholder 마우스 호버 효과
    placeholder.addEventListener("mouseenter", function () {
        placeholder.style.backgroundColor = "#dddddd";
        placeholder.style.color = "gray";
    });

    placeholder.addEventListener("mouseleave", function () {
        placeholder.style.backgroundColor = "#f8f8f8";
        placeholder.style.color = "#424949";
    });

    //-------------------------------------------- editForm관련 js--------------------------------------------//
    if (contentBox && contentBox.children.length === 0) {
        contentBox.innerHTML = hiddenContent.value;
    }

    const ctgInput = document.getElementById("boardCategory");
    if(ctgInput.value.length > 0) {
        const temp =  document.getElementById("temp");
        placeholder.innerHTML = ctgInput.value + ' <div class="boardWritePageDown"></div>'; // UI 업데이트
        ctgInput.value = temp.value;
    }
})
