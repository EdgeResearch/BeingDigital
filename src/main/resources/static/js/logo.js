document.addEventListener('scroll', function() {
    const logo = document.getElementById('header-logo');
    const mainlogo = document.getElementById('logo');

    if (window.scrollY > 400) {
        logo.style.display = 'flex';
        logo.classList.add('scrolled');
        mainlogo.style.display = 'none';
    } else {
        logo.style.display = 'none';
        logo.classList.remove('scrolled');
        mainlogo.style.display = 'flex';
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const scrollButton = document.querySelector('#scrollButton');

    if (scrollButton) {
        scrollButton.addEventListener('click', () => {
            const targetElement = document.querySelector('#main');
            const offset = 0; // Modifica questo valore per scrollare più o meno in basso

            window.scrollTo({
                top: targetElement.offsetTop + offset,
                behavior: 'smooth'
            });
        });
    }
});
