document.addEventListener('DOMContentLoaded', () => {
    const menus = document.querySelectorAll('.topMainMenu');

    menus.forEach(menu => {
        const word = menu.querySelector('.topMenuEachBox a');
        const submenu = menu.querySelector('.topSubMenu');
        const links = submenu?.querySelectorAll('.topSubMenuEach') || [];

        menu.addEventListener('mouseover', () => {
            if(word) word.style.color = 'gray';
            if (submenu) submenu.style.visibility = 'visible';
        });

        menu.addEventListener('mouseout', () => {
            if(word) word.style.color = 'black';
            if (submenu) submenu.style.visibility = 'hidden';
        })

        links.forEach(link => {
            const subWord = link.querySelector('a');

            link.addEventListener('mouseover', () => {
                link.style.backgroundColor = 'whitesmoke';
                if(subWord) subWord.style.color = 'gray';
            });

            link.addEventListener('mouseout', () => {
                link.style.backgroundColor = 'white';
                if(subWord) subWord.style.color = "#1b2631";
            });
        });
    });

    const profileBox = document.getElementById('profile');
    const profileSubBox = document.getElementById('profileSubBox');
    profileBox.addEventListener('click', () => {
        profileSubBox.style.display = profileSubBox.style.display === 'flex' ? 'none' : 'flex';
    })

    document.addEventListener("click", (e) => {
        if(!profileSubBox.contains(e.target) && !profileBox.contains(e.target)) {
            profileSubBox.style.display = 'none';
        }
    })

    const profileBtn = document.querySelectorAll('.profileBtn');

    profileBtn.forEach(box => {
        box.addEventListener('mouseover', (e) => {
            const btn = e.target.closest('.profileBtn').getElementsByTagName('button')[0];
            btn.style.backgroundColor = 'whitesmoke';
            btn.style.color = 'gray';
        })

        box.addEventListener('mouseout', (e) => {
            const btn = e.target.closest('.profileBtn').getElementsByTagName('button')[0];
            btn.style.backgroundColor = '#dcdcdc';
            btn.style.color = 'black';
        })
    })

})
