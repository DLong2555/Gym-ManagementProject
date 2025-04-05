document.addEventListener('DOMContentLoaded', () => {
    const myPageForm = document.getElementById('memProfile');

    myPageForm.addEventListener('click', () => {
        location.href="/gym/myPage";
    })

    const childInfoShowBtns = document.querySelectorAll('.myPageDownIcon');

    childInfoShowBtns.forEach(showBtn => {
        showBtn.addEventListener('click', (e) => {
            const childInfo = e.target.closest('.childInfoBox').querySelector('.childInfoEachBox');
            const currentDisplay = window.getComputedStyle(childInfo).display;

            const editBtn = e.target.closest('.childInfoBox').querySelector('.editIcon');
            const deleteBtn = e.target.closest('.childInfoBox').querySelector('.deleteIcon');

            if (currentDisplay === 'none') {
                childInfo.style.display = 'block';
                e.target.style.transform = 'rotate(180deg)';

                editBtn.style.display = 'block';
                deleteBtn.style.display = 'block';
            } else {
                if(editBtn.style.display === 'none') {
                    alert("수정 중 입니다.");
                    return false;
                }

                childInfo.style.display = 'none';
                e.target.style.transform = 'rotate(0deg)';

                editBtn.style.display = 'none';
                deleteBtn.style.display = 'none';
            }
        })
    })

    const phone = document.querySelector('.phoneNumber');

    const editbtns = document.querySelectorAll('.editIcon');
    const savebtns = document.querySelectorAll('.saveIcon');
    const deletebtns = document.querySelectorAll('.deleteIcon');
    const cancelbtns = document.querySelectorAll('.cancelIcon');

    const previousMap = new Map();

    editbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const childInfoBox = e.target.closest('.childInfoBox');
            editMode(childInfoBox, previousMap);

        });
    });

    cancelbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const childInfoBox = e.target.closest('.childInfoBox');
            viewMode(childInfoBox, previousMap);
        });
    });

    savebtns.forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const childInfoBox = e.target.closest('.childInfoBox');

            const id = childInfoBox.querySelector('.id');
            const name = childInfoBox.querySelector('.name');
            const age = childInfoBox.querySelector('.age');
            const phone = childInfoBox.querySelector('.phoneNumber');

            let gender = childInfoBox.querySelector(".genderBox input[type='radio']:checked").value;

            let editData = {
                id: id.value,
                name: name.value,
                age: age.value,
                gender: gender,
                phoneNumber: phone.value.replace(/\D/g, '')
            }

            try{
                const response = await fetch("/gym/myPage/child/edit", {
                    method: 'POST',
                    headers: {"content-type": "application/json"},
                    body: JSON.stringify(editData)
                });

                if(!response.ok){
                    let errors = await response.json();
                    childInfoBox.querySelectorAll('.field-error').forEach(e => e.remove());

                    for (let field in errors) {
                        let inputField = childInfoBox.querySelector(`[name="${field}"]`).closest('.myPageMyContents');

                        if (inputField) {
                            let errorDiv = document.createElement('div');
                            errorDiv.className = 'field-error';
                            errorDiv.style.color = 'red';
                            errorDiv.textContent = "X " + errors[field];

                            inputField.insertAdjacentElement('afterend', errorDiv);
                        }
                    }

                    return false;
                }else{
                    childInfoBox.querySelectorAll('.field-error').forEach(e => e.remove());

                    save(childInfoBox, previousMap);
                }

            }catch (error){

            }

        })
    })


    phone.addEventListener('keyup', (e) => {
        let value = e.target.value.replace(/\D/g, '');
        let formatted = '';

        if(e.key !== "backspace") {
            if (value.length > 0) formatted = value.slice(0, 3);
            if (value.length > 3) formatted += ' - ' + value.slice(3, 7);
            if (value.length > 7) formatted += ' - ' + value.slice(7, 11);
        }

        e.target.value = formatted;

    });



})

function viewMode(target, previousMap) {
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');

    target.querySelectorAll('.field-error').forEach(e => e.remove());

    editBtn.style.display = 'block';
    deleteBtn.style.display = 'block';
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const age = target.querySelector('.age');
    const gender = target.querySelector('.gender');
    const genderBox = target.querySelector('.genderBox');
    const phone = target.querySelector('.phoneNumber');

    name.readOnly = true;
    name.style.backgroundColor = "white";

    age.readOnly = true;
    age.style.backgroundColor = "white";

    phone.readOnly = true;
    phone.style.backgroundColor = "white";

    gender.style.display = 'block';
    genderBox.style.visibility = 'hidden';

    if (previousMap.has(id.value)) {
        const previous = previousMap.get(id.value);
        name.value = previous.name;
        age.value = previous.age;
        phone.value = previous.phone;

        if(previous.gender === "남자"){
            target.querySelector(".male").checked = true;
            target.querySelector(".female").checked = false;
        }else{
            target.querySelector(".male").checked = false;
            target.querySelector(".female").checked = true;
        }

        previousMap.delete(id.value);
    }
}

function editMode(target, previousMap) {
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');

    editBtn.style.display = 'none';
    deleteBtn.style.display = 'none';
    saveBtn.style.display = 'block';
    cancelBtn.style.display = 'block';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const age = target.querySelector('.age');
    const gender = target.querySelector('.gender');
    const genderBox = target.querySelector('.genderBox');
    const phone = target.querySelector('.phoneNumber');

    name.readOnly = false;
    name.style.backgroundColor = "lightgray";

    age.readOnly = false;
    age.style.backgroundColor = "lightgray";

    phone.readOnly = false;
    phone.style.backgroundColor = "lightgray";

    gender.style.display = 'none';
    genderBox.style.visibility = 'visible';

    if(!previousMap.has(id.value)){
        previousMap.set(id.value, {
            name:name.value,
            age:age.value,
            gender:gender.value,
            phone:phone.value,
        });
    }
}

function save(target, previousMap){
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');

    editBtn.style.display = 'block';
    deleteBtn.style.display = 'block';
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const age = target.querySelector('.age');
    const gender = target.querySelector('.gender');
    const genderBox = target.querySelector('.genderBox');
    const phone = target.querySelector('.phoneNumber');
    const selectedGender = target.querySelector(".genderBox input[type='radio']:checked").value;

    name.readOnly = true;
    name.style.backgroundColor = "white";

    age.readOnly = true;
    age.style.backgroundColor = "white";

    phone.readOnly = true;
    phone.style.backgroundColor = "white";

    gender.style.display = 'block';
    genderBox.style.visibility = 'hidden';

    if (selectedGender === "MALE") gender.value = "남자";
    else gender.value = "여자";

    previousMap.delete(id.value);
}

