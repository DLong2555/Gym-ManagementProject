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
                childInfo.style.display = 'none';
                e.target.style.transform = 'rotate(0deg)';

                editBtn.style.display = 'none';
                deleteBtn.style.display = 'none';
            }
        })
    })

    const name = document.querySelector('.name');
    const age = document.querySelector('.age');
    const gender = document.querySelector('.gender');
    const phone = document.querySelector('.phoneNumber');

    const editbtns = document.querySelectorAll('.editIcon');
    const savebtns = document.querySelectorAll('.saveIcon');
    const deletebtns = document.querySelectorAll('.deleteIcon');
    const cancelbtns = document.querySelectorAll('.cancelIcon');

    editbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            const childInfoBox = e.target.closest('.childInfoBox');

            const editBtn = childInfoBox.querySelector('.editIcon');
            const saveBtn = childInfoBox.querySelector('.saveIcon');
            const deleteBtn = childInfoBox.querySelector('.deleteIcon');
            const cancelBtn = childInfoBox.querySelector('.cancelIcon');

            editBtn.style.display = 'none';
            deleteBtn.style.display = 'none';
            saveBtn.style.display = 'block';
            cancelBtn.style.display = 'block';

            const name = childInfoBox.querySelector('.name');
            const age = childInfoBox.querySelector('.age');
            const gender = childInfoBox.querySelector('.gender');
            const genderBox = childInfoBox.querySelector('.genderBox');
            const phone = childInfoBox.querySelector('.phoneNumber');

            name.readOnly = false;
            age.readOnly = false;
            phone.readOnly = false;

            gender.style.display = 'none';
            genderBox.style.visibility = 'visible';
        })
    })

    cancelbtns.forEach(btn => {
        btn.addEventListener('click', (e) => {

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

