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

                    let result = request.response;

                    if (result.length === 0) {
                        console.log(error);
                    }else{
                        if(confirm("추가 등록하시겠습니까?")){
                            const addChild = new FormData();
                            addChild.append("gymId", gymId);
                            addChild.append("gymName", gymName);

                            console.log(addChild);

                            request.open("POST", "/gym/child/form", false);
                            request.send(addChild);
                        }else{
                            window.location.href="/";
                        }
                    }
                }
            }
        }

        request.open("POST", "/gym/child/new", false);
        request.send(formData);
    })


})