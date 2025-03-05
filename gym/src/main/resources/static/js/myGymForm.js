document.addEventListener('DOMContentLoaded', () => {
    const myPageForm = document.getElementById('memProfile');

    myPageForm.addEventListener('click', () => {
        location.href = "/gym/myPage";
    })

    const gymInfoShowBtns = document.querySelectorAll('.myPageDownIcon');

    gymInfoShowBtns.forEach(showBtn => {
        showBtn.addEventListener('click', (e) => {
            const gymInfo = e.target.closest('.gymInfoBox').querySelector('.gymInfoEachBox');
            const currentDisplay = window.getComputedStyle(gymInfo).display;

            const editBtn = e.target.closest('.gymInfoBox').querySelector('.editIcon');
            const deleteBtn = e.target.closest('.gymInfoBox').querySelector('.deleteIcon');

            if (currentDisplay === 'none') {
                gymInfo.style.display = 'block';
                e.target.style.transform = 'rotate(180deg)';

                editBtn.style.display = 'block';
                deleteBtn.style.display = 'block';
            } else {
                if (editBtn.style.display === 'none') {
                    alert("수정 중 입니다.");
                    return false;
                }

                gymInfo.style.display = 'none';
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
    const addressbtns = document.querySelectorAll('.addressIcon');

    const previousMap = new Map();

    editbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const gymInfoBox = e.target.closest('.gymInfoBox');
            editMode(gymInfoBox, previousMap);
        });
    });

    cancelbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const gymInfoBox = e.target.closest('.gymInfoBox');
            gymInfoBox.querySelectorAll('.field-error').forEach(e => e.remove());
            viewMode(gymInfoBox, previousMap);
        });
    });

    savebtns.forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const gymInfoBox = e.target.closest('.gymInfoBox');

            const id = gymInfoBox.querySelector('.id');
            const name = gymInfoBox.querySelector('.name');
            const price = gymInfoBox.querySelector('.price');
            const phone = gymInfoBox.querySelector('.phone');
            const zipcode = gymInfoBox.querySelector('.zipcode');
            const roadName = gymInfoBox.querySelector('.roadName');
            const detailAddress = gymInfoBox.querySelector('.detailAddress');

            let editData = {
                gymId: id.value,
                gymName: name.value,
                price: price.value,
                gymPhoneNum: phone.value.replace(/\D/g, ''),
                zipCode: zipcode.value,
                roadName: roadName.value,
                detailAddress: detailAddress.value
            }

            try {
                const response = await fetch("/gym/myPage/myGym/edit", {
                    method: 'POST',
                    headers: {"content-type": "application/json"},
                    body: JSON.stringify(editData)
                });

                if (!response.ok) {
                    let errors = await response.json();
                    gymInfoBox.querySelectorAll('.field-error').forEach(e => e.remove());

                    for (let field in errors) {
                        let inputField = gymInfoBox.querySelector(`[name="${field}"]`).closest('.myPageMyContents');

                        if (inputField) {
                            let errorDiv = document.createElement('div');
                            errorDiv.className = 'field-error';
                            errorDiv.style.color = 'red';
                            errorDiv.textContent = "X " + errors[field];

                            inputField.insertAdjacentElement('afterend', errorDiv);
                        }
                    }

                    return false;
                } else {
                    gymInfoBox.querySelectorAll('.field-error').forEach(e => e.remove());

                    save(gymInfoBox, previousMap);
                }

            } catch (error) {

            }

        })
    })

    addressbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();

            const addressBox = e.target.closest('.gymInfoBox');
            const zipcode = addressBox.querySelector('.zipcode');
            const roadName = addressBox.querySelector('.roadName');
            const detailAddress = addressBox.querySelector('.detailAddress');

            new daum.Postcode({
                oncomplete: function (data) {
                    let address = "";

                    if (data.userSelectedType === 'R') address = data.roadAddress;
                    else address = data.jibunAddress;

                    console.log(address);
                    zipcode.value = data.zonecode;
                    roadName.value = address;

                    detailAddress.value = "";
                    detailAddress.focus();
                }
            }).open();
        })
    })

    const phones = document.querySelectorAll('.phone');
    phones.forEach(phone => {
        phone.addEventListener('keyup', (e) => {
            let value = e.target.value.replace(/\D/g, '');
            let formatted = value;

            if (value.startsWith('010') && value.length >= 8) {
                formatted = value.slice(0, 3) + ' - ' + value.slice(3, 7);
                if (value.length >= 8) formatted += ' - ' + value.slice(7, 11);
            } else if (value.startsWith('02')) {
                if (value.length > 2) formatted = '02 -' + value.slice(2, 5);
                if (value.length > 5) formatted += ' - ' + value.slice(5, 10);
            } else {
                if (value.length > 3) formatted = value.slice(0, 3) + '-' + value.slice(3, 6);
                if (value.length > 6) formatted += '-' + value.slice(6, 10);
            }

            e.target.value = formatted;

        });
    })
})

function viewMode(target, previousMap) {
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');
    const addressBtn = target.querySelector('.addressIcon');

    editBtn.style.display = 'block';
    deleteBtn.style.display = 'block';
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
    addressBtn.style.display = 'none';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const price = target.querySelector('.price');
    const phone = target.querySelector('.phone');
    const zipcode = target.querySelector('.zipcode');
    const roadName = target.querySelector('.roadName');
    const detailAddress = target.querySelector('.detailAddress');

    name.readOnly = true;
    name.style.backgroundColor = "white";

    price.readOnly = true;
    price.style.backgroundColor = "white";

    phone.readOnly = true;
    phone.style.backgroundColor = "white";

    detailAddress.readOnly = true;
    detailAddress.style.backgroundColor = "white";


    if (previousMap.has(id.value)) {
        const previous = previousMap.get(id.value);
        name.value = previous.name;
        price.value = previous.price;
        phone.value = previous.phone;
        zipcode.value = previous.zipcode;
        roadName.value = previous.roadName;
        detailAddress.value = previous.detailAddress;

        previousMap.delete(id.value);
    }
}

function editMode(target, previousMap) {
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');
    const addressBtn = target.querySelector('.addressIcon');

    editBtn.style.display = 'none';
    deleteBtn.style.display = 'none';
    saveBtn.style.display = 'block';
    cancelBtn.style.display = 'block';
    addressBtn.style.display = 'block';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const price = target.querySelector('.price');
    const phone = target.querySelector('.phone');
    const zipcode = target.querySelector('.zipcode');
    const roadName = target.querySelector('.roadName');
    const detailAddress = target.querySelector('.detailAddress');

    name.readOnly = false;
    name.style.backgroundColor = "whitesmoke";

    price.readOnly = false;
    price.style.backgroundColor = "whitesmoke";

    phone.readOnly = false;
    phone.style.backgroundColor = "whitesmoke";

    detailAddress.readOnly = false;
    detailAddress.style.backgroundColor = "whitesmoke";

    if (!previousMap.has(id.value)) {
        previousMap.set(id.value, {
            name: name.value,
            price: price.value,
            phone: phone.value,
            zipcode: zipcode.value,
            roadName: roadName.value,
            detailAddress: detailAddress.value
        });
    }
}

function save(target, previousMap) {
    const editBtn = target.querySelector('.editIcon');
    const saveBtn = target.querySelector('.saveIcon');
    const deleteBtn = target.querySelector('.deleteIcon');
    const cancelBtn = target.querySelector('.cancelIcon');
    const addressBtn = target.querySelector('.addressIcon');

    editBtn.style.display = 'block';
    deleteBtn.style.display = 'block';
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
    addressBtn.style.display = 'none';

    const id = target.querySelector('.id');
    const name = target.querySelector('.name');
    const price = target.querySelector('.price');
    const phone = target.querySelector('.phone');
    const detailAddress = target.querySelector('.detailAddress');

    name.readOnly = true;
    name.style.backgroundColor = "white";

    price.readOnly = true;
    price.style.backgroundColor = "white";

    phone.readOnly = true;
    phone.style.backgroundColor = "white";

    detailAddress.readOnly = true;
    detailAddress.style.backgroundColor = "white";

    previousMap.delete(id.value);
}

