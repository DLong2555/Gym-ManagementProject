document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('registerForm').addEventListener('submit', event => {
        event.preventDefault();

        const gymId = document.getElementById("gymId").value;
        const gymName = document.getElementById('gymName').value;

        let form = document.getElementById("registerForm");
        let formData = new FormData(form);

        let request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                if (request.status === 200) {

                    if (request.response.success === "success") {
                        console.log("response: " + request.response);

                        if(confirm("등록 성공! 추가 등록하시겠습니까?")){
                            const form = document.createElement("form");
                            form.method = "POST";
                            form.action = "/gym/child/form";

                            const gymIdInput = document.createElement("input");
                            gymIdInput.type = "hidden";
                            gymIdInput.name = "gymId";
                            gymIdInput.value = gymId;

                            const gymNameInput = document.createElement("input");
                            gymNameInput.type = "hidden";
                            gymNameInput.name = "gymName";
                            gymNameInput.value = gymName;

                            form.appendChild(gymIdInput);
                            form.appendChild(gymNameInput);
                            document.body.appendChild(form);

                            form.submit();
                        }else{
                            window.location.href="/";
                        }
                    }else{
                        let result = request.response;
                        console.log(result);

                        document.querySelectorAll(".field-error").forEach(field => {
                            field.textContent = "";
                        })

                        Object.keys(result).forEach((key) => {
                            let field = document.getElementById(key);

                            if(field){
                                let errorField = field.closest('.joinInputDiv').querySelector('.field-error');

                                if (errorField) {
                                    errorField.textContent = "X " + result[key];
                                }
                            }
                        })

                        return false;
                    }
                }
            }
        }

        request.responseType = "json";
        request.open("POST", "/gym/child/new", true);
        request.send(formData);
    })


})