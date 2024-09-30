let lastClickX = 0; // 최근 클릭 X 좌표
let lastClickY = 0; // 최근 클릭 Y 좌표
let nowZoom = 1; // 현재 비율 (1 = 100%)
let isDragging = false; // 드래그 상태
let startX, startY; // 드래그 시작 좌표
let translateX = 0; // 이동한 X 거리
let translateY = 0; // 이동한 Y 거리

// 확대 버튼 클릭
function zoomIn() {
    // 드래그 이벤트 충돌 방지 -> 좌표 초기화 필요
    resetDragCoordinates();
    // 확대할 때 좌표 업데이트 하지 않음
    nowZoom += 0.3; // 비율을 30% 늘림
    if (nowZoom > 3) nowZoom = 3;
    zooms();
}

// 축소 버튼 클릭
function zoomOut() {
    // 드래그 이벤트 충돌 방지 -> 좌표 초기화 필요
    resetDragCoordinates();
    // 축소할 때 좌표 업데이트 하지 않음
    nowZoom -= 0.1; // 비율을 10% 줄임
    if (nowZoom < 0.9) nowZoom = 0.9;
    zooms();
}

// 클릭 시 좌표를 설정하는 함수
function setLastClickCoordinates(e) {
    /*offset 값 체크*/
    // console.log("svgContainer.offsetLeft" + svgContainer.offsetLeft);
    // console.log("svgContainer.offsetTop" + svgContainer.offsetTop);
    // svg 영역 크기만큼 좌표 보정
    lastClickX = e.clientX-svgContainer.offsetLeft; // 전체 페이지 기준 클릭 위치
    lastClickY = e.clientY-svgContainer.offsetTop;
}

function zooms() {
    console.log(lastClickX, lastClickY);
    svgContainer.style.transform = `scale(${nowZoom})`;
    svgContainer.style.transformOrigin = `${lastClickX}px ${lastClickY}px`; // 클릭 지점을 기준으로 설정
}


// 원래 화면크기도 되돌아가기
function zoomReset() {
    // 드래그 이벤트 충돌 방지 -> 좌표 초기화 필요
    resetDragCoordinates();
    nowZoom = 1; // 초기 비율로 리셋
    zooms();
}

// 이벤트 리스너 등록
document.getElementById("zoomInButton").addEventListener("click", zoomIn);
document.getElementById("zoomOutButton").addEventListener("click", zoomOut);

// SVG 영역에서 클릭 시 좌표 설정
svgContainer.addEventListener("click", setLastClickCoordinates);

// 드래그 시작
function startDrag(e) {
    isDragging = true;
    startX = e.pageX; // 드래그 시작 X 좌표
    startY = e.pageY; // 드래그 시작 Y 좌표
    svgContainer.style.userSelect = 'none'; // 드래그로 텍스트 선택 비활성화
}

// 드래그 중
function drag(e) {
    if (!isDragging) return;
    e.preventDefault(); // 기본 동작 방지

    const walkX = (e.pageX - startX); // 드래그 거리
    const walkY = (e.pageY - startY);

    translateX += walkX; // 이동 거리 업데이트
    translateY += walkY;

    svgContainer.style.transform = `scale(${nowZoom}) translate(${translateX}px, ${translateY}px)`; // SVG 이동

    startX = e.pageX; // 시작 좌표 업데이트
    startY = e.pageY; // 시작 좌표 업데이트
}

// 드래그 종료
function endDrag() {
    isDragging = false;
    svgContainer.style.userSelect = ''; // 드래그로 텍스트 선택 기능 복원
}

// 드래그 이벤트 충돌 방지 -> 좌표 초기화 필요
function resetDragCoordinates(){
    startX = 0;
    startY = 0;
    translateY = 0;
    translateY = 0;
}

// 이벤트 리스너 등록
svgContainer.addEventListener("mousedown", startDrag);
svgContainer.addEventListener("mousemove", drag);
svgContainer.addEventListener("mouseup", endDrag);
svgContainer.addEventListener("mouseleave", endDrag);