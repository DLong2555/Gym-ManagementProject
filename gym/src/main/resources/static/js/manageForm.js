document.addEventListener("DOMContentLoaded", function () {

    highlightFirstValue();

    mouseEffect();

    highlight();

    showParents();

    phoneNumberFormatter();

    calculateLeftDate(null);

    const editBtn = document.querySelectorAll('.editButton');
    const bulkEditBtn = document.getElementById('bulkEdit');
    const allCheckBox = document.getElementById('labelBox').getElementsByTagName('input')[0];

    const originalData = new Map();
    let editCount = 0;

    editBtn.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const childInfo = e.target.closest('.childInfo');
            let id = childInfo.querySelector('.childId');

            if (e.target.dataset.edit === "false") {

                if(!originalData.has(id)) {
                    originalData.set(id, {
                        belt:childInfo.querySelector('.belt').value,
                        startDate: childInfo.querySelector('.startDate').value,
                        endDate: childInfo.querySelector('.endDate').value
                    })
                }

                e.target.dataset.edit = "true";
                e.target.textContent = '취소';
                e.target.style.backgroundColor = 'black';
                e.target.style.color = 'white';

                childInfo.querySelector('.checkbox').checked = true;
                childInfo.querySelector('.belt').readOnly = false;
                childInfo.querySelector('.belt').style.backgroundColor = 'lightgray';
                childInfo.querySelector('.startDate').readOnly = false;
                childInfo.querySelector('.endDate').readOnly = false;
                editCount++;

                if (bulkEditBtn.dataset.edit === "false") {
                    bulkEditBtn.textContent = "저장";
                    bulkEditBtn.dataset.edit = "true";
                }
            } else {
                e.target.dataset.edit = "false";
                e.target.textContent = '수정';
                e.target.style.backgroundColor = 'lightgray';
                e.target.style.color = 'black';

                if(originalData.has(id)) {
                    const original = originalData.get(id);
                    childInfo.querySelector('.belt').value = original.belt;
                    childInfo.querySelector('.startDate').value = original.startDate;
                    childInfo.querySelector('.endDate').value = original.endDate;

                    originalData.delete(id);
                }

                childInfo.querySelector('.checkbox').checked = false;
                childInfo.querySelector('.belt').readOnly = true;
                childInfo.querySelector('.belt').style.backgroundColor = 'white';
                childInfo.querySelector('.startDate').readOnly = true;
                childInfo.querySelector('.endDate').readOnly = true;
                editCount--;

                if (editCount === 0) {
                    bulkEditBtn.textContent = "일괄 수정";
                    bulkEditBtn.dataset.edit = "false";

                    if(allCheckBox.checked) allCheckBox.checked = false;

                }
            }
        });
    });


    allCheckBox.addEventListener('change', () => {
        let checkBox = document.querySelectorAll('.myMemberListBox:not([style*="display: none"]) .checkbox');

        if (allCheckBox.checked) {
            checkBox.forEach(check => {
                if (!check.checked){
                    check.checked = true;
                }
            });
        } else {
            checkBox.forEach(check => {
                if (check.checked){
                    check.checked = false;}
            });
        }
    });

    bulkEditBtn.addEventListener('click', () => {
        let checkBox = document.querySelectorAll('.myMemberListBox:not([style*="display: none"]) .checkbox');

        if(bulkEditBtn.dataset.edit === "false"){
            checkBox.forEach(check => {
                if (check.checked){
                    let target = check.closest('.childInfo');
                    let id = target.querySelector('.childId');

                    if(!originalData.has(id)) {
                        originalData.set(id, {
                            belt:target.querySelector('.belt').value,
                            startDate: target.querySelector('.startDate').value,
                            endDate: target.querySelector('.endDate').value
                        })
                    }

                    target.querySelector('.editButton').dataset.edit = "true";
                    target.querySelector('.editButton').textContent = "취소";
                    target.querySelector('.editButton').style.backgroundColor = 'black';
                    target.querySelector('.editButton').style.color = 'white';


                    target.querySelector('.belt').readOnly = false;
                    target.querySelector('.belt').style.backgroundColor = 'lightgray';
                    target.querySelector('.startDate').readOnly = false;
                    target.querySelector('.endDate').readOnly = false;

                    editCount++;
                }
            })

            bulkEditBtn.textContent = "저장";
            bulkEditBtn.dataset.edit = "true";
        }else{
            let editChildForm = [];

            checkBox.forEach(check => {
                if (check.checked){
                    let target = check.closest('.childInfo');

                    if(target.querySelector('.editButton').dataset.edit === "true") {
                        let editChildInfo = {
                            id: target.querySelector('.childId').value,
                            belt: target.querySelector('.belt').value,
                            startDate: target.querySelector('.startDate').value,
                            endDate: target.querySelector('.endDate').value,
                        };

                        editChildForm.push(editChildInfo);
                    }
                }
            })

            if(confirm("변경 내용을 저장하시겠습니까?")) {

                let request = new XMLHttpRequest();
                request.onreadystatechange = function () {
                    if (request.readyState === 4) {
                        if (request.status === 200) {
                            let response = request.response;

                            for(let id of response.successIds){
                                for(let target of checkBox){
                                    let child = target.closest('.childInfo');

                                    if(child.querySelector('.childId').value === String(id)){
                                        child.querySelector('.editButton').dataset.edit = "false";
                                        child.querySelector('.editButton').textContent = "수정";
                                        child.querySelector('.editButton').style.backgroundColor = "lightgray";
                                        child.querySelector('.editButton').style.color = "black";

                                        child.querySelector('.checkbox').checked = false;
                                        child.querySelector('.belt').readOnly = true;
                                        child.querySelector('.belt').style.backgroundColor = 'white';
                                        child.querySelector('.startDate').readOnly = true;
                                        child.querySelector('.endDate').readOnly = true;

                                        editCount--;
                                        break;
                                    }
                                }
                            }

                            calculateLeftDate(response.successIds);

                            if (editCount === 0) {
                                bulkEditBtn.textContent = "일괄 수정";
                                bulkEditBtn.dataset.edit = "false";

                                if(allCheckBox.checked) allCheckBox.checked = false;

                            }
                        }
                    }
                }

                request.open("POST", "/gym/manage", true);
                request.responseType = "json";
                request.setRequestHeader("Content-Type", "application/json");
                request.send(JSON.stringify(editChildForm));
            }
        }
    })
})

function highlightFirstValue() {
    let gym = document.querySelectorAll('.gymName')[0];
    gym.style.backgroundColor = 'lightgray';
    gym.style.color = 'black';
    gym.style.fontWeight = 'bold';

    showCheckedGym(gym.dataset.value);
}

function mouseEffect() {
    let gyms = document.querySelectorAll('.gymName');
    let parentsBtn = document.querySelectorAll('.parentDisplayBtn button');
    let editBtn = document.querySelectorAll('.editButton');

    gyms.forEach(gym => {
        gym.addEventListener('mouseover', function () {
            if (gym.style.fontWeight !== 'bold') {
                gym.style.backgroundColor = 'whitesmoke';
                gym.style.color = 'gray';
            }
        });

        gym.addEventListener('mouseout', function () {
            if (gym.style.fontWeight !== 'bold') {
                gym.style.backgroundColor = 'white';
                gym.style.color = 'black';
            }
        });
    })

    parentsBtn.forEach(btn => {
        btn.addEventListener('mouseover', function () {
            btn.style.backgroundColor = '#dcdcdc';
            btn.style.color = 'gray';
        });

        btn.addEventListener('mouseout', function () {
            btn.style.backgroundColor = 'lightgray';
            btn.style.color = 'black';
        });
    })

    editBtn.forEach(btn => {
        btn.addEventListener('mouseover', function () {
            if (btn.dataset.edit === "false") {
                btn.style.backgroundColor = '#dcdcdc';
                btn.style.color = 'gray';
            }else{
                btn.style.backgroundColor = '#333333';
                btn.style.color = 'whitesmoke';
            }
        });

        btn.addEventListener('mouseout', function () {
            if (btn.dataset.edit === "false") {
                btn.style.backgroundColor = 'lightgray';
                btn.style.color = 'black';
            }else{
                btn.style.backgroundColor = 'black';
                btn.style.color = 'white';
            }
        });
    })
}

function highlight() {
    let gyms = document.querySelectorAll('.gymName');
    let bulkEditBtn = document.getElementById('bulkEdit');

    gyms.forEach(gym => {
        gym.addEventListener('click', (e) => {

            if(e.target.style.fontWeight !== 'bold') {
                let checkbox = document.querySelectorAll('.checkbox');
                let allCheckbox = document.getElementById('labelBox').getElementsByTagName('input')[0];

                if (bulkEditBtn.dataset.edit === "true") {
                    alert("수정을 완료해주세요.");
                    return false;
                }

                checkbox.forEach(check => {
                    check.checked = false;
                    allCheckbox.checked = false;
                })

                gyms.forEach(g => {
                    g.style.backgroundColor = 'white';
                    g.style.color = 'black';
                    g.style.fontWeight = 'normal';
                })

                gym.style.backgroundColor = 'lightgray';
                gym.style.color = 'black';
                gym.style.fontWeight = 'bold';

                showCheckedGym(gym.dataset.value);
            }
        })
    })
}

function showParents() {
    let parentsButtons = document.querySelectorAll('.parentDisplayBtn button');

    parentsButtons.forEach(button => {
        button.addEventListener('click', (e) => {

            let parents = e.target.closest('.myMemberListBox').querySelector('.parentsBox');
            let currentDisplay = window.getComputedStyle(parents).display;

            if (currentDisplay !== 'none') parents.style.display = 'none';
            else parents.style.display = 'flex';
        })
    })
}

function showCheckedGym(gymName) {
    let members = document.querySelectorAll('.myMemberListBox');

    members.forEach(member => {

        if (member.dataset.value === gymName) member.style.display = 'block';
        else member.style.display = 'none';

    })
}

function calculateLeftDate(ids) {
    let endDate = document.querySelectorAll('.endDate');

    function getDiff(date){
        let end = new Date(date.value);
        let now = new Date();

        end.setHours(0, 0, 0, 0);

        now.setHours(0, 0, 0, 0);

        return (end - now) / (1000 * 60 * 60 * 24);
    }

    function updateColor(name, diff){
        if (diff < 0) name.style.color = 'red';
        else if (diff >= 0 && diff <= 7) name.style.color = 'orange';
        else name.style.color = 'black';
    }
    console.log(ids);
    endDate.forEach(date => {
        let childInfo = date.closest('.childInfo');
        let name = childInfo.querySelector('.name');
        let childId = childInfo.querySelector('.childId').value;

        if(ids && ids.includes(parseInt(childId))){
            console.log(childId);
            let diff = getDiff(date);
            updateColor(name, diff);
        }else if (!ids){
            let diff = getDiff(date);
            updateColor(name, diff);
        }

    })
}

function phoneNumberFormatter() {
    let phoneNumber = document.querySelectorAll('.phoneNumber');

    phoneNumber.forEach(phoneNumber => {
        if (phoneNumber.value !== null) {
            phoneNumber.value = phoneNumber.value.substring(0, 3) + " - "
                + phoneNumber.value.substring(3, 7) + " - "
                + phoneNumber.value.substring(7, phoneNumber.value.length);
        }
    })
}



