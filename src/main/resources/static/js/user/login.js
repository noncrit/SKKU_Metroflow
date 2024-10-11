const disableSpacebar = (event) => {
    if (event.code === "Space") {
        event.preventDefault();
    }
};
// 한글 입력 시 제거
const disableKorean = (event) => {
    const value = event.target.value;
    if (/[\u3131-\uD79D]/.test(value)) {
        // 한글이 포함되어 있다면 제거
        event.target.value = value.replace(/[\u3131-\uD79D]/g, '');
    }
};
window.addEventListener('load', function () {
    const inputFields = document.getElementsByTagName("input");
    const nickname = document.getElementById('nickname');

    Array.from(inputFields).forEach(input => {
        input.addEventListener('keydown', disableSpacebar);

        if (nickname !== input) {
            input.addEventListener('input', disableKorean);
        }
    });
});