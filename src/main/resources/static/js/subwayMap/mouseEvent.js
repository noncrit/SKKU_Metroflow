const subwayMap = document.getElementsByClassName('subwayMap')[0];

subwayMap.addEventListener('mouseenter', () => {
    console.log("mouseEnter ON");
    subwayMap.style.cursor = 'url(/images/mouse/cursor_convert.cur), auto';
});

subwayMap.addEventListener('mouseleave', () => {
    subwayMap.style.cursor = 'auto'; // 기본 커서로 돌아가기
});
