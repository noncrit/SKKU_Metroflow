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
                    modalContent.querySelector(".nowTime").innerText = new Date().toLocaleTimeString();

                    // 기존 내용 초기화 (테이블 헤더 제외)
                    // const existingRows = modalBody.querySelectorAll("tr:not(:first-child)"); // 첫 번째 행을 제외하고 제거
                    const existingRows = modalBody.querySelectorAll("tr"); // 테이블 row 초기화
                    existingRows.forEach(row => row.remove()); // 기존 행 제거

                    // 데이터 항목 추가
                    data.forEach(stationInfo => {
                        const row = document.createElement("tr");

                        // 호선 -> svg 객체로 바꾸기 위한 변수 처리
                        const station_line = stationInfo.station_line;
                        // 받아온 station_line을 #S1, #S2 이런 형태의 String으로 변환
                        const station_line_svg = "#S"+station_line;
                        // console.log("check : ", station_line_svg);

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
                        svgWrapper.setAttribute("viewBox", "0 -30 30 30"); // 예시: (x, y, width, height)
                        svgWrapper.setAttribute("style", "overflow:visible; width: 40px; height: 40px;"); // 크기를 조절
                        svgWrapper.appendChild(useElement); // SVG 요소를 래퍼에 추가

                        // 셀에 SVG 추가
                        const cellWithSvg = document.createElement("td");
                        cellWithSvg.appendChild(svgWrapper); // SVG 래퍼를 셀에 추가

                        row.appendChild(cellWithSvg); // 행에 셀 추가

                        row.innerHTML += `
                            <td id="td_direction_type">${stationInfo.direction_type}</td>
                            <td id="td_congestion">혼잡도: ${stationInfo.congestion} %</td>
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

                    // 화면 중앙 좌표 받아오기
                    const windowWidth = window.innerWidth;
                    const windowHeight = window.innerHeight;
                    const modalWidth = 750; // 모달의 너비
                    
                    // 모달 위치 보정
                    modal.style.left = `${((windowWidth - modalWidth) / 2) - 280}px`; // x 위치
                    modal.style.top = `${((windowHeight - modal.offsetHeight) / 2) - 350}px`; // y 위치

                    // 모달 표시
                    modal.style.width = `${modalWidth}px`;
                    modal.style.display = "block";

                } else {
                    console.error("Error fetching station info: ", response.statusText);
                }
            } catch (error) {
                console.error("Error fetching station info:", error);
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
