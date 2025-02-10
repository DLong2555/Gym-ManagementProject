document.addEventListener("DOMContentLoaded", function() {

    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 쿠키값 없으면 공백.
    document.getElementById("memId").value = getCookie("userId");

    // ID가 있는경우 아이디 저장 체크박스 체크
    if(document.getElementById("memId").value !== ""){
        document.getElementById("idSave").checked = true;
    }

    // 아이디 저장하기 체크박스 onchange
    const checkId = document.getElementById("idSave");

    checkId.onchange = function (event) {
        if(checkId.checked){ //checked true
            const userId = document.getElementById("memId").value;
            setCookie("userId", userId, 30); // 30일 동안 쿠키 보관
        }else{ //checked false
            deleteCookie("userId");
        }
    };

    // 아이디 저장하기가  눌린상태에서, ID를 입력한 경우
    const idInput = document.getElementById("memId");

    idInput.addEventListener("keyup", function(e) {
        if(checkId.checked){ //checked true
            const userId = document.getElementById("memId").value;
            setCookie("userId", userId, 30); // 30일 동안 쿠키 보관
        }
    })

    function setCookie(cookieName, value, days){
        const date = new Date();
        date.setDate(date.getDate() + days);

        const cookieValue = encodeURIComponent(value) + ((days==null) ? "" : "; expires=" + date.toUTCString());
        document.cookie = cookieName + "=" + cookieValue;
    }

    function deleteCookie(cookieName){
        const expireDate = new Date();
        expireDate.setDate(expireDate.getDate() - 1);
        document.cookie = cookieName + "= " + "; expires=" + expireDate.toUTCString();
    }

    function getCookie(cookieName) {
        cookieName = cookieName + '=';
        const cookieData = document.cookie;
        let start = cookieData.indexOf(cookieName);
        let cookieValue = '';
        if(start !== -1){
            start += cookieName.length;
            let end = cookieData.indexOf(';', start);
            if(end === -1)end = cookieData.length;
            cookieValue = cookieData.substring(start, end);
        }
        return decodeURIComponent(cookieValue);
    }
})