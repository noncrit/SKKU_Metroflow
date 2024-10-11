const subwayMap = document.getElementsByClassName('subwayMap');

subwayMap.addEventListener('mouseenter', () => {
    subwayMap.style.cursor = 'url(path/to/your/cursor-image.png), auto';
});

subwayMap.addEventListener('mouseleave', () => {
    subwayMap.style.cursor = 'auto'; // 기본 커서로 돌아가기
});
