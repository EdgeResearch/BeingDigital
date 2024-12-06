document.addEventListener('DOMContentLoaded', function () {
    const cards = document.querySelectorAll('.card');
    let activeDropdown = null;
    let timeoutId;

    function showDropdown(dropdownId, triggerElement) {
        clearTimeout(timeoutId);
        if (activeDropdown) {
            activeDropdown.style.display = 'none';
        }
        const dropdown = document.getElementById(dropdownId);
        activeDropdown = dropdown;

        const rect = triggerElement.getBoundingClientRect();

        dropdown.style.position = 'absolute';
        dropdown.style.left = `${rect.left + window.scrollX - 5}px`;
        dropdown.style.top = `${rect.bottom + window.scrollY + 10}px`;
        dropdown.style.display = 'grid';
    }

    function scheduleHideDropdown(dropdownId) {
        timeoutId = setTimeout(function () {
            const dropdown = document.getElementById(dropdownId);
            dropdown.style.display = 'none';
            activeDropdown = null;
        }, 200);
    }


    cards.forEach(function (card) {
        const dropdownId = card.getAttribute('data-dropdown');


        card.addEventListener('mouseenter', function () {
            showDropdown(dropdownId, card);
        });


        card.addEventListener('mouseleave', function () {
            scheduleHideDropdown(dropdownId);
        });


        const dropdown = document.getElementById(dropdownId);
        dropdown.addEventListener('mouseenter', function () {
            clearTimeout(timeoutId);
        });
        dropdown.addEventListener('mouseleave', function () {
            scheduleHideDropdown(dropdownId);
        });
    });
});
