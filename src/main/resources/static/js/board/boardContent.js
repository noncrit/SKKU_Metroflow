document.addEventListener('DOMContentLoaded', function () {
    let thumbsUp = document.getElementById("thumbsUp").value.toLowerCase() === "true"; // 현재 유저가 그 보드에 대해 좋아요를 눌렀는지 true / false
    let thumbsDown = document.getElementById("thumbsDown").value.toLowerCase() === "true"; // 현재 유저가 그 보드에 대해 싫어요를 눌렀는지 true / false
    let hiddenUp = document.getElementById('thumbsUp').value.toLowerCase() === "true"; // 현재 유저가 그 보드에 대해 좋아요를 눌렀는지 true / false
    let hiddenDown = document.getElementById('thumbsDown').value.toLowerCase() === "true"; // 현재 유저가 그 보드에 대해 싫어요를 눌렀는지 true / false
    const thumbsUpContainer = document.getElementById('thumbsUpContainer'); // 좋아요 컨테이너
    const thumbsDownContainer = document.getElementById('thumbsDownContainer'); // 싫어요 컨테이너
    let thumbsUpCount = document.getElementById('thumbsUpCount'); // 좋아요 수
    let thumbsDownCount = document.getElementById('thumbsDownCount'); // 싫어요 수
    const aLink = document.querySelectorAll('a'); // a 태그 선택자
    const boardNo = document.getElementById('boardNo').value; // 해당 보드의 번호
    const priorThumbsUp = document.getElementById('priorThumbsUp').value.toLowerCase() === "true"; // 이전 좋아요가 true 인지
    const priorThumbsDown = document.getElementById('priorThumbsDown').value.toLowerCase() === "true"; // 이전 싫어요가 true 인지
    const buttonLink = document.querySelectorAll('button'); // 버튼 태그 선택자
    const header = document.querySelector('meta[name="_csrf_header"]').content; // csrf header
    const token = document.querySelector('meta[name="_csrf"]').content; // csrf token

    judgePriorThumbs(thumbsUp, thumbsUpContainer, thumbsDown, thumbsDownContainer);
    thumbsUpContainer.addEventListener('click', function () { // 좋아요 컨테이너 클릭 시
        thumbsUp = !thumbsUp; // 스위치 기능 작동(현재 좋아요가 true 였다면 클릭시 false)
        if (thumbsUp === true && thumbsDown === true) { // false 에서 true 가 된 좋아요고, 싫어요가 true 였다면
            thumbsDown = false; // 싫어요 false
            console.log("before one : " + thumbsUpCount.value)
            thumbsUpPlus(thumbsUpContainer, thumbsUpCount);
            thumbsDownMinus(thumbsDownContainer, thumbsDownCount);
        } else if (thumbsUp === true) { // false 에서 true 가 된 좋아요, false 였던 싫어요
            thumbsUpPlus(thumbsUpContainer, thumbsUpCount);
        } else { // 그 외
            thumbsUpMinus(thumbsUpContainer, thumbsUpCount);
        }
        hiddenDown = thumbsDown;
        hiddenUp = thumbsUp;
    });

    thumbsDownContainer.addEventListener('click', function () { // 싫어요 컨테이너
        thumbsDown = !thumbsDown;
        if (thumbsDown === true && thumbsUp === true) {
            thumbsUp = false;
            thumbsDownPlus(thumbsDownContainer, thumbsDownCount);
            thumbsUpMinus(thumbsUpContainer, thumbsUpCount);
        } else if (thumbsDown === true) {
            thumbsDownPlus(thumbsDownContainer, thumbsDownCount);
        } else {
            thumbsDownMinus(thumbsDownContainer, thumbsDownCount);
        }
        hiddenDown = thumbsDown;
        hiddenUp = thumbsUp;
    })

    // a태그 클릭 시 경로 지정
    aLink.forEach(link => {
        link.addEventListener('click', function (e) {
            let linkUrl = new URL(link.href).pathname;
            alert("URL : " + linkUrl);
            e.preventDefault()
            goRecommendation(linkUrl, boardNo, hiddenUp, hiddenDown, priorThumbsUp, priorThumbsDown, header, token)
        }); // a태그 url, 보드 번호, 유저가 그 보드에 대해 좋아요를 눌렀는지,
        // 유저가 그 보드에 대해 싫어요를 눌렀는지, 이전에 좋아요를 눌렀는지, 이전에 싫어요를 눌렀는지 에 대한 파라미터도
        // 같이 넘겨줌
    });
    buttonLink.forEach(link => {
        link.addEventListener('click', function (e) {
            let linkUrl = new URL(link.form.action).pathname;
            e.preventDefault()
            if (link.form.id === "button_delete") {
                const confirmation = confirm('정말로 삭제하시겠습니까?');

                if (!confirmation) {
                    e.preventDefault();
                    // 사용자가 취소를 선택하면 삭제하지 않음
                } else {
                    goRecommendation(linkUrl, boardNo, hiddenUp, hiddenDown, priorThumbsUp, priorThumbsDown, header, token);
                    alert('삭제가 완료되었습니다.');
                }
            } else {
                goRecommendation(linkUrl, boardNo, hiddenUp, hiddenDown, priorThumbsUp, priorThumbsDown, header, token);
            }
        }); // form 태그 url, 보드 번호, 유저가 그 보드에 대해 좋아요를 눌렀는지,
        // 유저가 그 보드에 대해 싫어요를 눌렀는지, 이전에 좋아요를 눌렀는지, 이전에 싫어요를 눌렀는지 에 대한 파라미터도
        // 같이 넘겨줌
    });

});

// Recommendation 컨트롤러 호출 함수
function goRecommendation(linkUrl, boardNo, hiddenUp, hiddenDown, priorThumbsUp, priorThumbsDown, header, token) {
    const url = "/board/recommendation"
    const data = {
        url: linkUrl,
        boardNo: boardNo,
        isThumbsUp: hiddenUp,
        isThumbsDown: hiddenDown,
        priorThumbsUp: priorThumbsUp,
        priorThumbsDown: priorThumbsDown,
    }; // 전송할 데이터
    console.log(header);
    console.log(token);

    fetch(url, {
        method: "POST",
        headers: {
            // 'header': header,
            [header]: token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data) // 데이터를 JSON 문자열로 변환
    })
        .then(response => {
            if (response.redirected) {
                // 리디렉션이 발생한 경우
                window.location.href = response.url; // 리디렉션 URL로 이동
            } else {
                return response.json(); // 리디렉션이 아닌 경우 JSON 응답 처리
            }
        })
        .then(data => {
            console.log("응답 데이터:", data);
        })
        .catch(error => {
            console.error("문제가 발생했습니다:", error);
        });
}
function thumbsUpPlus(thumbsUpContainer, thumbsUpCount) {
    thumbsUpContainer.classList.add('thumbsUpActive'); // 강조 효과
    thumbsUpCount.text = String(parseInt(thumbsUpCount.text) + 1); // 카운트 증가
    thumbsUpCount.value = String(parseInt(thumbsUpCount.value) + 1); // 카운트 증가
}

function thumbsUpMinus(thumbsUpContainer, thumbsUpCount) {
    thumbsUpContainer.classList.remove('thumbsUpActive'); // 강조 효과
    thumbsUpCount.text = String(parseInt(thumbsUpCount.text) - 1); // 카운트 감소
    thumbsUpCount.value = String(parseInt(thumbsUpCount.value) - 1); // 카운트 감소
}

function thumbsDownPlus(thumbsDownContainer, thumbsDownCount) {
    thumbsDownContainer.classList.add('thumbsDownActive');
    thumbsDownCount.text = String(parseInt(thumbsDownCount.text) + 1); // 카운트 증가
    thumbsDownCount.value = String(parseInt(thumbsDownCount.value) + 1); // 카운트 증가
}

function thumbsDownMinus(thumbsDownContainer, thumbsDownCount) {
    thumbsDownContainer.classList.remove('thumbsDownActive'); // 강조 효과
    thumbsDownCount.text = String(parseInt(thumbsDownCount.text) - 1); // 카운트 감소
    thumbsDownCount.value = String(parseInt(thumbsDownCount.value) - 1); // 카운트 감소
}

function judgePriorThumbs(thumbsUp, thumbsUpContainer, thumbsDown, thumbsDownContainer) {
    if (thumbsUp === true) { // 유저가 해당 보드에 대해 좋아요가 true 이면
        thumbsUpContainer.classList.add('thumbsUpActive'); // 좋아요 css 이벤트 작동
    } else if (thumbsDown === true) { // 유저가 해당 보드에 대해 싫어요가 true 이면
        thumbsDownContainer.classList.add('thumbsDownActive') // 싫어요 css 이벤트 작동
    }
}

