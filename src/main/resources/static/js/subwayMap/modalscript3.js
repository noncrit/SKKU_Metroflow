const svgContainer = document.getElementById("svgContainer");
const modal = document.getElementById("myModal");
const modalContent = modal.querySelector(".modal-content");
const modalBody = modalContent.querySelector(".modal-body table"); // 테이블을 직접 참조
const closeModal = document.querySelector(".closeTab");

// SVG 네임스페이스 정의
const SVG_NS = "http://www.w3.org/2000/svg";
const XLINK_NS = "http://www.w3.org/1999/xlink";

document.addEventListener("DOMContentLoaded", function() {
    // 텍스트 클릭 이벤트 리스너 추가
    svgContainer.addEventListener("click", async function (event) {
        const target = event.target;

        if (target.tagName === "text") {
            const clickedStation = target.textContent; // 클릭한 역 이름

            try {
                // 서버에서 데이터 가져오기
                const response = await fetch(`/station-info?stationName=${encodeURIComponent(clickedStation)}`);
                if (response.ok) {
                    const data = await response.json();

                    // 역 이름 설정
                    const stationName = data[0].station_name; // 첫 번째 데이터에서 역 이름 추출
                    modalContent.querySelector("#stationName").innerText = stationName; // 헤더에 역 이름 설정

                    // 현재 시간 설정
                    modalContent.querySelector(".nowTime").innerText =
                        new Date().toLocaleTimeString([], { hour: 'numeric', minute: 'numeric', hour12: true });

                    // modal 내용물 초기화
                    clearModal(modalBody);

                    // 데이터 항목 추가
                    data.forEach(stationInfo => {
                        const row = document.createElement("tr");

                        // 받아온 호선 -> #S1, #S2 형태로 포맷팅하는 함수
                        const station_line_svg = getStationLineSVG(stationInfo.station_line);

                        // use 요소 생성
                        const useElement = document.createElementNS(SVG_NS, "use");
                        // <use xlink="http://www.w3.org/1999/xlink"> 태그에 href="S1" 같은 속성을 추가하는 스크립트
                        useElement.setAttributeNS(XLINK_NS, "href", `${station_line_svg}`);

                        // y축 반전 변환 추가
                        useElement.setAttribute("transform", "scale(1, -1) translate(0, -1)"); // 15는 반전된 원의 높이의 절반

                        // overflow 옵션 ( 크기 넘칠 시 )
                        // useElement.setAttribute("style", "overflow:visible;");

                        // SVG 태그 생성
                        const svgWrapper = document.createElementNS(SVG_NS, "svg");
                        svgWrapper.setAttribute("viewBox", "0 -30 30 30"); // 파라미터: (min-x, min-y, width, height)
                        svgWrapper.setAttribute("style", "overflow:visible; width: 40px; height: 40px;"); // 크기를 조절
                        svgWrapper.appendChild(useElement); // SVG 요소를 래퍼에 추가

                        // 셀에 SVG 추가
                        const cellWithSvg = document.createElement("td");
                        cellWithSvg.appendChild(svgWrapper); // SVG 래퍼를 셀에 추가

                        row.appendChild(cellWithSvg); // 행에 셀 추가

                        row.innerHTML += `
                            <td id="td_direction_type">${stationInfo.direction_type}</td>
                            <td id="td_congestion">혼잡도 : ${stationInfo.congestion} %</td>
                        `;

                        // style 조정을 위한 접근
                        const svgCell = row.children[0]; // 역마크 svg
                        const directionCell = row.children[1]; // 방향
                        const congestionCell = row.children[2]; // 혼잡도

                        // style 조정
                        svgCell.style.width = "20%";

                        directionCell.style.width = "20%";
                        directionCell.style.textAlign = "left";

                        congestionCell.style.width = "60%";
                        congestionCell.style.textAlign = "left";

                        modalBody.appendChild(row); // 모달 테이블에 행 추가
                    });

                    showModal(modal,550);

                // 불러올 데이터가 없는 역 예외처리
                } else {
                    modalContent.querySelector("#stationName").innerText = clickedStation;
                    const row = document.createElement("tr");
                    // modal 테이블 내용물 초기화
                    clearModal(modalBody);
                    // 혼잡도 정보가 없는 역에 대한 메시지 추가
                    addNoInfoMessage(modalBody,"혼잡도 정보가 없는 역입니다.")
                    // 모달 보이도록 좌표, 표시 속성 설정
                    showModal(modal,550);
                    console.error("Station has no information: ", response.statusText);
                }
            } catch (error) {
                modalContent.querySelector("#stationName").innerText = clickedStation;
                const row = document.createElement("tr");
                // modal 테이블 내용물 초기화
                clearModal(modalBody);
                // 혼잡도 정보가 없는 역에 대한 메시지 추가
                addNoInfoMessage(modalBody,"혼잡도 정보가 없는 역입니다.")
                // 모달 보이도록 좌표, 표시 속성 설정
                showModal(modal,750);
                console.error("Station has no information: ", response.statusText);
            }
        }
    });
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

// 호선 정보를 받아서 String으로 포맷팅해서 반환
function getStationLineSVG(station_line) {
    return `#S${station_line}`;
}

//모달창 테이블 초기화 함수
function clearModal(modalBody){
    // 기존 내용 초기화 (테이블 헤더 제외)
    const existingRows = modalBody.querySelectorAll("tr"); // 테이블 row 초기화
    existingRows.forEach(row => row.remove()); // 기존 행 제거
}

// 모달창 표시 함수
function showModal(modal, modalWidth) {
    // 화면 중앙 좌표 받아오기
    const windowWidth = window.innerWidth;
    const windowHeight = window.innerHeight;

    // 모달 위치 보정
    modal.style.left = `${((windowWidth - modalWidth) / 2) - 280}px`; // x 위치
    modal.style.top = `${((windowHeight - modal.offsetHeight) / 2) - 350}px`; // y 위치

    // 모달 표시
    modal.style.width = `${modalWidth}px`;
    modal.style.display = "block";
}

// 정보가 없는 역 접근시 메시지 설정용 함수
function addNoInfoMessage(modalBody, message) {
    const row = document.createElement("tr"); // 새로운 행 생성
    const msgCell = document.createElement("td"); // 새로운 셀 생성
    msgCell.id = "td_noInfo"; // ID 설정
    msgCell.innerText = message; // 텍스트 설정

    row.appendChild(msgCell); // 셀을 행에 추가
    modalBody.appendChild(row); // 행을 모달 본문에 추가
}   

