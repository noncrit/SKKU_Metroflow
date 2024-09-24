const svgContainer = document.getElementById("svgContainer");
const modal = document.getElementById("myModal");
const modalText = document.getElementById("modalText");
const closeModal = document.querySelector(".closeTab");
// let nowZoom = 1; // 현재 비율 (1 = 100%)

// 텍스트 클릭 이벤트 리스너 추가
svgContainer.addEventListener("click", function(event) {
    const target = event.target;

    if (target.tagName === "text") {
        // 클릭한 텍스트의 내용을 모달에 표시
        modalText.innerText = target.textContent;

        // 마우스 클릭 위치를 기준으로 모달 위치 계산
        let modalX = event.clientX; // 클릭한 X좌표
        let modalY = event.clientY - modal.offsetHeight; // 클릭한 Y좌표에서 모달 높이만큼 위로 이동

        // 화면의 오른쪽 경계를 고려하여 모달 위치 조정
        const windowWidth = window.innerWidth;
        if (modalX + modal.offsetWidth > windowWidth) {
            modalX = windowWidth - modal.offsetWidth; // 오른쪽 경계에 맞추기
        }

        // 모달 위치 설정
        modal.style.left = `${modalX}px`;
        modal.style.top = `${modalY}px`;

        // 모달 가로 크기 변경
        // 타임리프로 렌더링하면 css 씹히는 현상있어서 스크립트 처리
        modal.style.width="20%";
        modal.style.display = "block"; // 모달 표시
    }
});

// 모달 닫기 기능
closeModal.addEventListener("click", function() {
    modal.style.display = "none"; // 모달 숨김
});

// 모달 외부 클릭 시 닫기
window.addEventListener("click", function(event) {
    if (event.target === modal) {
        modal.style.display = "none"; // 모달 숨김
    }
});

// ESC 키로 모달 닫기
window.addEventListener("keydown", function(event) {
    if (event.key === "Escape" && modal.style.display === "block") {
        modal.style.display = "none"; // 모달 숨김
    }
});
