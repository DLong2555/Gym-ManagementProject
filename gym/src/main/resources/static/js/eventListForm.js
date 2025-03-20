document.addEventListener('DOMContentLoaded', () => {

    const myGyms = document.querySelectorAll('.myGym');
    const eventLinks = document.querySelectorAll('.eventLink');
    const eventNames = document.querySelectorAll('.eventName');

    const urlParams = new URLSearchParams(location.search);
    const eventId = urlParams.get('eventId');

    const gymId = urlParams.get('gymId');

    console.log(gymId);
    myGyms.forEach(link => {
        console.log(link.dataset.gym);

        if(link.dataset.gym === gymId){
            console.log(gymId);
            let gymDiv = link.closest('.myGymList');
            gymDiv.style.backgroundColor = 'lightgray';
            gymDiv.style.fontWeight = '600';
            link.style.color = 'black';
        }
    });

    eventLinks.forEach(link => {
        if(link.dataset.event === eventId) {
            let eventDiv = link.closest('.eventName');
            eventDiv.style.backgroundColor = 'lightgray';
            eventDiv.style.fontWeight = '600';
        }
    });

    eventNames.forEach(name => {
        name.addEventListener('click', () => {
            window.location.href = name.querySelector('a').href;
        })
    })

})



