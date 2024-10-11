let count = parseInt(document.getElementById('noticeCount').value); // 긴급 공지 게시물 수;
let pagingButtons = document.querySelectorAll('.paging');
let optionValue = document.getElementById('boardOption').value;

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('boardOption').addEventListener('change', function () {
        optionValue = document.getElementById('boardOption').value;
        selectOption(optionValue);
    })
    pagingButtons.forEach(button => {
        let baseUrl = button.getAttribute('href').split('?')[0];
        // alert("baseurl1 : " + baseUrl);
        baseUrl += ("?" + button.getAttribute('href').split('?')[1]);
        // alert("baseurl2 : " + baseUrl);
        button.setAttribute('href', `${baseUrl}&boardOption=${optionValue}`)
        // alert("buttonLink : " + button.getAttribute('href'));
        // alert("a태그 링크 : " + button);
    })
    const rows = document.querySelectorAll('tr[id^="boardRow_"]')
    rows.forEach(row => {
        let isSelected;
        const boardNo = row.id.split('_')[1]; // ID에서 boardNo 추출
        const isNoticeSelected = document.getElementById(boardNo).value.toLowerCase() === "true";
        const isNoticeSelectContainer = document.getElementById('noticeSelectionDiv' + boardNo);
        if (isNoticeSelected === true) { // 이미 긴급공지로 선택됐을 시
            isNoticeSelectContainer.classList.add('activeSelection'); // 클래스 activeSelection 추가 => css 효과 on
            isSelected = true;
        } else {
            isSelected = false;
        }

        isNoticeSelectContainer.addEventListener('click', function () {
            isSelected = !isSelected;
            if (isSelected) {
                console.log("9minute : " + count);
                if (count < 3) {
                    isNoticeSelectContainer.classList.add('activeSelection');
                    selectNotice(boardNo);
                } else {
                    alert('최대 공지 수를 넘겼습니다!')
                    isSelected = !isSelected;
                    // console.log("현재 카운트 : " + count)
                }

            } else {
                isNoticeSelectContainer.classList.remove('activeSelection');
                deleteNotice(boardNo);

            }
        })
    });
})

// 게시물 삭제 함수
function deleteBoard(boardNo, trBoardNo) {
    trBoardNo.remove();
    fetch(`/admin/board/delete?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            // console.log('응답 성공!')
            if (response.redirected) {
                // console.log('redirect 시도!')
                window.location.href = "/board";
            } else {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 형태로 응답받기
        })
        .then(data => {
            // console.log('Success:', data);

        })
        .catch((error) => {
            // console.error('Error:', error + '에러남!');
        });
}

// 게시물 긴급공지로 등록 함수
function selectNotice(boardNo) {
    fetch(`/admin/notice/insert?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 형태로 응답받기
        })
        .then(data =>{
            console.log("데이터 값 : " + data);
            count = parseInt(JSON.stringify(data));
        })
        .catch((error) => {
            // console.error('Error:', error + '에러남!');
        });
}


// 게시물 긴급공지에서 삭제 함수
function deleteNotice(boardNo) {
    fetch(`/admin/notice/delete?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 형태로 응답받기
        })
        .then(data => {
            // console.log('Success:', data);
            count = parseInt(JSON.stringify(data));
            console.log("삭제 카운팅 " + count);
        })
        .catch((error) => {
            // console.error('Error:', error + '에러남!');
        });
}

function selectOption(selectedOption) {
    fetch(`/board/selectOption?selectedOption=${selectedOption}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            window.location.href=response.url;
        }
        return response.json(); // JSON 형태로 응답받기
    })
        .then(data => {
            console.log("제이슨 데이터 : " + JSON.stringify(data));
            // console.log('Success:', data);
        })
        .catch((error) => {
            // console.error('Error:', error + '에러남!');
        });
}
