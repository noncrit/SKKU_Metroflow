document.addEventListener('DOMContentLoaded', function () {
    const selectElement = document.getElementById('line-select');

    selectElement.addEventListener('change', function () {
        // 기존 클래스 제거
        selectElement.classList.remove(
            'line-1', 'line-2', 'line-3', 'line-4',
            'line-5', 'line-6', 'line-7', 'line-8', 'line-9'
        );

        // 선택된 값에 따라 클래스 추가
        switch (selectElement.value) {
            case '1호선':
                selectElement.classList.add('line-1');
                break;
            case '2호선':
                selectElement.classList.add('line-2');
                break;
            case '3호선':
                selectElement.classList.add('line-3');
                break;
            case '4호선':
                selectElement.classList.add('line-4');
                break;
            case '5호선':
                selectElement.classList.add('line-5');
                break;
            case '6호선':
                selectElement.classList.add('line-6');
                break;
            case '7호선':
                selectElement.classList.add('line-7');
                break;
            case '8호선':
                selectElement.classList.add('line-8');
                break;
            case '9호선':
                selectElement.classList.add('line-9');
                break;
            default:
                // 기본 배경색으로 변경
                selectElement.style.backgroundColor = 'white';
                selectElement.style.color = 'black';
        }
    });
});
