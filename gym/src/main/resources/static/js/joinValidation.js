document.addEventListener("DOMContentLoaded", () => {
    const password = document.querySelector('#password');
    const passwordChk = document.querySelector('#password-chk');
    const error_pattern_word = document.querySelector('.pattern_word');
    const error_pattern_len = document.querySelector('.pattern_len');

    const includeCondition = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9])/;
    const lengthCondition = /^.{8,16}$/;

    //password validation
    password.addEventListener('keyup', () => {
        const value = password.value;

        if (!value){
            error_pattern_word.innerHTML = "<p class='.pattern_word' style=\"color : gray\">&#10003; 영문/숫자/특수문자 포함</p>";
            error_pattern_len.innerHTML = "<p class=\"pattern_len\" style=\"color : gray\">&#10003; 8자 이상 16자 이하 입력 (공백 제외)</p>";
            error_pattern_word.style.color = 'gray';
        }

        if(!includeCondition.test(password.value) && password.value){
            error_pattern_word.innerHTML = "<p class='.pattern_word' style=\"color : red\">&#215; 영문/숫자/특수문자 포함</p>"
        }else if(includeCondition.test(password.value) && password.value){
            error_pattern_word.innerHTML = "<p class='.pattern_word' style=\"color : green\">&#10003; 영문/숫자/특수문자 포함</p>"
        }

        if(!lengthCondition.test(password.value) && password.value) {
            error_pattern_len.innerHTML = "<p class=\"pattern_len\" style=\"color : red\">&#215; 8자 이상 16자 이하 입력 (공백 제외)</p>";
        }else if(lengthCondition.test(password.value) && password.value){
            error_pattern_len.innerHTML = "<p class=\"pattern_len\" style=\"color : green\">&#10003; 8자 이상 16자 이하 입력 (공백 제외)</p>";
        }
    })

    //password check validation
    passwordChk.addEventListener('keyup', () => {
        const pwdChkError = passwordChk.closest('.inputGroup').querySelector('.field-error');
        const pwdChk = passwordChk.value;
        const pwd = password.value;

        if(pwd !== pwdChk && pwdChk) pwdChkError.innerHTML = "<p style='color : red'>&#215; 비밀번호가 일치하지 않습니다.</p>"
        else pwdChkError.innerHTML = "";
    })

    //id duplication check - ajax
    const idValue = document.querySelector('#memId');

    idValue.addEventListener('blur', function(){
        const idError = idValue.closest('.inputGroup').querySelector('.field-error');

        let reqJson = {};
        reqJson.memId = idValue.value;

        let request = new XMLHttpRequest();

        request.onreadystatechange = function(){

            if(request.readyState === 4){
                console.log("response: " + request.response);

                if(request.status === 200){
                    let result = request.response;

                    if (!result){
                        console.log("error: " + result);
                        return;
                    }

                    idError.classList.remove('error', 'success');

                    if(result.errors){
                        idError.textContent = "X " + result.errors;
                        idError.classList.add('error');
                    } else if(!result.result){
                        idError.textContent = "✔ 사용 가능한 아이디입니다.";
                        idError.classList.add('success');
                    }
                    else{
                        idError.textContent = "X 사용할 수 없는 아이디입니다.";
                        idError.classList.add('error');
                    }
                }
            }
        }

        request.open('POST', '/gym/duplicationIdCheck', true);
        request.responseType = 'json';
        request.setRequestHeader('Content-Type', 'application/json');
        request.send(JSON.stringify(reqJson));
    });

})

