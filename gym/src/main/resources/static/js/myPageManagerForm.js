document.addEventListener('DOMContentLoaded', () => {
    const name = document.getElementById('name');
    const phone = document.getElementById('phoneNumber');

    const editBtn = document.getElementById('editBtn');
    const saveBtn = document.getElementById('saveBtn');
    const cancelBtn = document.getElementById('cancelBtn');

    let previousName = "";
    let previousPhoneNumber = "";
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

    editBtn.addEventListener('click', () => {

        previousName = name.value;
        previousPhoneNumber = phone.value;

        name.readOnly = false;
        name.style.backgroundColor = "whitesmoke";

        phone.readOnly = false;
        phone.style.backgroundColor = "whitesmoke";

        editBtn.style.display = 'none';
        saveBtn.style.display = 'block';
        cancelBtn.style.display = 'block';
    })

    cancelBtn.addEventListener('click', () => {
        document.querySelectorAll('.field-error').forEach((e) => e.remove());

        name.value = previousName;
        phone.value = previousPhoneNumber;

        name.readOnly = true;
        name.style.backgroundColor = "white";

        phone.readOnly = true;
        phone.style.backgroundColor = "white";

        editBtn.style.display = 'block';
        saveBtn.style.display = 'none';
        cancelBtn.style.display = 'none';
    })

    saveBtn.addEventListener('click', async (e) => {

        let formData = {
            name: name.value,
            phoneNumber: phone.value.replace(/ - /g, ""),
        };

        try {
            const response = await fetch('/gym/myPage/manager/edit', {
                method: 'POST',
                headers: {"content-type": "application/json; charset=UTF-8"},
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const errors = await response.json();
                console.log(errors);

                document.querySelectorAll('.field-error').forEach((e) => e.remove());

                for (let field in errors) {
                    let inputField = document.querySelector(`[name="${field}"]`).closest('.myPageMyContents');
                    console.log(inputField);
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
                document.querySelectorAll('.field-error').forEach((e) => e.remove());
                name.readOnly = true;
                name.style.backgroundColor = "white";

                phone.readOnly = true;
                phone.style.backgroundColor = "white";

                editBtn.style.display = 'block';
                saveBtn.style.display = 'none';
                cancelBtn.style.display = 'none';
            }

        } catch (err) {

        }


    });

    const myGymForm = document.getElementById('myGymForm');

    myGymForm.addEventListener('click', () => {
        location.href="/gym/myPage/myGym";
    })
})

