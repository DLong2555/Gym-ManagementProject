document.addEventListener("DOMContentLoaded", () => {
    const addressBtn = document.querySelector(".address-btn");
    const zipcode = document.querySelector("#zipcode");
    const roadName = document.querySelector("#roadName");
    const detailAddress = document.querySelector("#detailAddress");
    const joinBtn =document.querySelectorAll("#joinManagerForm button");

    joinBtn.forEach(btn => {
        btn.addEventListener("mouseover", () => {
            btn.style.backgroundColor = "gray";

        })

        btn.addEventListener("mouseout", () => {
            btn.style.backgroundColor = "black";
        })
    })

    addressBtn.addEventListener("click", function(){
        new daum.Postcode({
            oncomplete: function(data){
                let address = "";

                if(data.userSelectedType === 'R') address = data.roadAddress;
                else address = data.jibunAddress;

                zipcode.value = data.zonecode;
                roadName.value = address;

                detailAddress.value = "";
                detailAddress.focus();
            }
        }).open();
    });
})