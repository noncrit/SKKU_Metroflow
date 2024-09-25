const svgContainer = document.getElementById("svgContainer");
const modal = document.getElementById("myModal");
const stationName = document.getElementById("stationName");
const nowTime = document.getElementById("nowtime");
const congestionLevelUptrack = document.getElementById("congestionLevel_uptrack");
const congestionLevelDowntrack = document.getElementById("congestionLevel_downtrack");
const closeModal = document.querySelector(".closeTab");

// 텍스트 클릭 이벤트 리스너 추가
svgContainer.addEventListener("click", function(event) {
    const target = event.target;

    if (target.tagName === "text") {
        // 클릭한 텍스트의 내용을 모달에 표시
        const clickedStation = target.textContent; // 클릭한 역 이름

        // 역 이름을 모달에 설정
        stationName.innerText = clickedStation;
        // 현재 시간 및 혼잡도 설정 (여기서는 예시로 고정값 사용)
        nowTime.innerText = new Date().toLocaleTimeString(); // 현재 시간
        congestionLevelUptrack.innerText = "상선 혼잡도 : 30"; // 예시 값
        congestionLevelDowntrack.innerText = "하선 혼잡도 : 30"; // 예시 값

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

        // 모달 가로 크기 설정
        modal.style.width = "20%";
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
