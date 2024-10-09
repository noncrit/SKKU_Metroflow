

function submitGoFavoriteList() {

    const button = document.querySelector('.time-select-button');
    const currentPage = button.getAttribute('data-current-page');
    const ampm = document.getElementById('ampm').value;
    const hour = document.getElementById('hour').value;
    const minute = document.getElementById('minute').value;

    console.log("submitGoFavoriteList 작동");
    // console.log(currentPage);
    // console.log(ampm);
    // console.log(hour);
    // console.log(minute);

    // URL 생성
    const url = `/goFavoriteListWithSetTime?page=${currentPage}&ampm=${ampm}&hour=${hour}&minute=${minute}`;

    console.log(url);
    location.href = url; // 페이지 이동
}

async function submitDeleteFavorite(event) {

    // data-delete-target 값 가져오기
    const stationId = event.target.getAttribute('data-delete-target');
    console.log(stationId);

    const userConfirmed = confirm("즐겨찾기를 삭제하시겠습니까?");
    if (userConfirmed) {
        const postData = {
            station_id: stationId
        };
        try {
            //csrf 토큰 -> 포함 안하면 POST 요청을 SpringSecurity에서 걸러버림
            const csrfToken = document.querySelector('meta[name="_csrf"]').content;
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
            // console.log("Token : " + csrfToken);
            // console.log("csrfHeader : " + csrfHeader);

            // fetch를 이용한 POST 요청
            const FavoriteResponse = await fetch('/deleteFromFavoriteList', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(postData) // 데이터 JSON 문자열로 변환
            });

            // 응답 상태에 따라 다르게 처리
            if (FavoriteResponse.ok) {
                alert("즐겨찾기 삭제 완료");
                const FavoriteResponseData = await FavoriteResponse.json(); // 성공적인 응답이면 JSON으로 변환
                // console.log('Success:', FavoriteResponseData); // 성공적으로 응답을 받았을 때 처리
                window.location.href = '/goFavoriteList';
            } else {
                // 오류 처리: 상태 코드에 따라 다른 메시지 출력 가능
                console.error('Error:', FavoriteResponse.status, FavoriteResponse.statusText);
            }
        } catch (error) {
            console.error('Fetch error:', error); // 오류 처리
        }
    }
}