document.addEventListener("DOMContentLoaded", function () {

    highlight();

    mouseEffect();

    showParents();

    calculateLeftDate(null);

    const editBtn = document.querySelectorAll('.editButton');
    const bulkEditBtn = document.getElementById('bulkEdit');
    const allCheckBox = document.querySelector('.allCheckBox');

    const originalData = new Map();
    let editCount = 0;

    editBtn.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const childInfo = e.target.closest('.childTr');
            let id = childInfo.querySelector('.childId');

            if (e.target.dataset.edit === "false") {

                if (!originalData.has(id)) {
                    console.log("new");
                    originalData.set(id, {
                        belt: childInfo.querySelector('.belt').value,
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

                if (originalData.has(id)) {
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

                    if (allCheckBox.checked) allCheckBox.checked = false;

                }
            }
        });
    });


    allCheckBox.addEventListener('change', () => {
        let checkBox = document.querySelectorAll('.checkbox');

        if (allCheckBox.checked) {
            if(bulkEditBtn.dataset.edit === "false") {
                checkBox.forEach(check => {
                    if (!check.checked) {
                        check.checked = true;
                    }
                });
            }
        } else {
            if (bulkEditBtn.dataset.edit === "false") {
                checkBox.forEach(check => {
                    if (check.checked) {
                        check.checked = false;
                    }
                });
            }
        }
    });

    bulkEditBtn.addEventListener('click', () => {
        let checkBox = document.querySelectorAll('.checkbox');
        let isCheckedBox = Array.from(checkBox).some(check => check.checked);

        if(isCheckedBox) {
            if (bulkEditBtn.dataset.edit === "false") {
                checkBox.forEach(check => {
                    if (check.checked) {
                        let target = check.closest('.childTr');
                        let id = target.querySelector('.childId');

                        if (!originalData.has(id)) {
                            originalData.set(id, {
                                belt: target.querySelector('.belt').value,
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
            } else {
                let editChildForm = [];

                checkBox.forEach(check => {
                    if (check.checked) {
                        let target = check.closest('.childTr');

                        if (target.querySelector('.editButton').dataset.edit === "true") {
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
                console.log(editChildForm);
                if (confirm("변경 내용을 저장하시겠습니까?")) {

                    let request = new XMLHttpRequest();
                    request.onreadystatechange = function () {
                        if (request.readyState === 4) {
                            if (request.status === 200) {
                                let response = request.response;

                                for (let id of response.successIds) {
                                    for (let target of checkBox) {
                                        let child = target.closest('.childTr');
                                        let childId = child.querySelector('.childId');

                                        if (childId.value === String(id)) {

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

                                            originalData.delete(childId);

                                            break;
                                        }
                                    }
                                }

                                calculateLeftDate(response.successIds);

                                if (editCount === 0) {
                                    bulkEditBtn.textContent = "일괄 수정";
                                    bulkEditBtn.dataset.edit = "false";

                                    if (allCheckBox.checked) allCheckBox.checked = false;

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
        }
    })
})

function highlight() {
    let gymId = window.location.pathname.split("/")[3];
    let gymNames = document.querySelectorAll('.gymName');

    gymNames.forEach((gym) => {
        if(gym.dataset.id === gymId){
            gym.style.backgroundColor = 'lightgray';
            gym.style.color = 'black';
            gym.style.fontWeight = 'bold';
        }
    })
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
            } else {
                btn.style.backgroundColor = '#333333';
                btn.style.color = 'whitesmoke';
            }
        });

        btn.addEventListener('mouseout', function () {
            if (btn.dataset.edit === "false") {
                btn.style.backgroundColor = 'lightgray';
                btn.style.color = 'black';
            } else {
                btn.style.backgroundColor = 'black';
                btn.style.color = 'white';
            }
        });
    })
}



function showParents() {
    let parentsButtons = document.querySelectorAll('.parentDisplayBtn button');

    parentsButtons.forEach(button => {
        button.addEventListener('click', (e) => {

            let parents = e.target.closest('.childTbody').querySelectorAll('.parentsBox');
            let displayChk = e.target.closest('.childTbody').querySelector('.parentsBox');
            let currentDisplay = window.getComputedStyle(displayChk).display;

            if (currentDisplay !== 'none'){
                parents.forEach(parent => {
                    parent.style.setProperty('display', 'none', 'important');
                })
            } else {
                parents.forEach(parent => {
                    parent.style.setProperty('display', 'table', 'important');
                })
            }
        })
    })
}

// function showCheckedGym(gymName) {
//     let members = document.querySelectorAll('.myMemberListBox');
//
//     members.forEach(member => {
//
//         if (member.dataset.value === gymName) member.style.display = 'block';
//         else member.style.display = 'none';
//
//     })
// }

function calculateLeftDate(ids) {
    let endDate = document.querySelectorAll('.endDate');

    function getDiff(date) {
        let end = new Date(date.value);
        let now = new Date();

        end.setHours(0, 0, 0, 0);

        now.setHours(0, 0, 0, 0);

        return (end - now) / (1000 * 60 * 60 * 24);
    }

    function updateColor(name, diff) {
        if (diff < 0) name.style.color = 'red';
        else if (diff >= 0 && diff <= 7) name.style.color = 'orange';
        else name.style.color = 'black';
    }

    endDate.forEach(date => {
        let childInfo = date.closest('.childTr');
        let name = childInfo.querySelector('.name');
        let childId = childInfo.querySelector('.childId').value;

        if (ids && ids.includes(parseInt(childId))) {
            let diff = getDiff(date);
            updateColor(name, diff);
        } else if (!ids) {
            let diff = getDiff(date);
            updateColor(name, diff);
        }

    })
}



