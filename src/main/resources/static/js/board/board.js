document.addEventListener('DOMContentLoaded', function () {
    const deleteButton = document.querySelector('.delete-btn');

    deleteButton.addEventListener('click', function (event) {
        const confirmation = confirm('정말로 삭제하시겠습니까?');

        if (!confirmation) {
            // 사용자가 취소를 선택하면 삭제하지 않음
            event.preventDefault();
        } else {
            // 삭제 진행 코드 추가 필요
            alert('삭제가 완료되었습니다.');
        }
    });
});
