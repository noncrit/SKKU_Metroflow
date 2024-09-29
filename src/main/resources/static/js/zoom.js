let nowZoom = 1; // 현재 비율 (1 = 100%)

// 화면크기 축소
function zoomOut() {
    nowZoom -= 0.1; // 비율을 10% 줄임
    
    // 화면크기 최대 축소율 0.9 (90%)
    if (nowZoom < 0.9) {
        nowZoom = 0.9;
    }
    zooms();
}

// 화면크기 확대
function zoomIn() {
    nowZoom += 0.2; // 비율을 20% 늘림
    
    // 화면크기 최대 확대율 2 (200%)
    if (nowZoom > 2) {
        nowZoom = 2; 
    }
    zooms();
}

// 원래 화면크기도 되돌아가기
function zoomReset() {
    nowZoom = 1; // 초기 비율로 리셋
    zooms();
}

function zooms() {
    // transform을 사용하여 비율 적용
    document.getElementById("svgContainer").style.transform = `scale(${nowZoom})`;
    document.getElementById("svgContainer").style.transformOrigin = "top left"; // 확대/축소 기준점 설정
}

