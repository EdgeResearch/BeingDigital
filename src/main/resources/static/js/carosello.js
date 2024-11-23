
/*
document.addEventListener('DOMContentLoaded', () => {
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');
    const slide = document.querySelector('.carousel-slide');
    const cardWidth = document.querySelector('.card').offsetWidth;
    const cardMargin = 20;
    let currentIndex = 0;

    function updateCarousel() {
        const offset = -currentIndex * (cardWidth + cardMargin);
        slide.style.transform = `translateX(${offset}px)`;
    }

    nextButton.addEventListener('click', () => {
        const totalCards = slide.children.length;
        if (currentIndex < totalCards - 2) {
            currentIndex++;
        } else {
            currentIndex = 0;
        }
        updateCarousel();
    });

    prevButton.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = slide.children.length - 3;
        }
        updateCarousel();
    });
});

*/

document.addEventListener('DOMContentLoaded', () => {
    $('.carousel-slide').slick({
        infinite: true,
        variableWidth:true,
        prevArrow:".prev",
        nextArrow:".next"
    });
});