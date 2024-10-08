let count = 0;
document.addEventListener('DOMContentLoaded', function () {
    const rows = document.querySelectorAll('tr[id^="boardRow_"]')
    rows.forEach(row => {
        let isSelected;
        const boardNo = row.id.split('_')[1]; // ID에서 boardNo 추출
        const isNoticeSelected = document.getElementById(boardNo).value.toLowerCase() === "true";
        const isNoticeSelectContainer = document.getElementById('noticeSelectionDiv' + boardNo);
        console.log(isNoticeSelected)
        if (isNoticeSelected === true) {
            isNoticeSelectContainer.classList.add('activeSelection');
            isSelected = true;
            count ++;
        } else {
            isSelected = false;
        }

        isNoticeSelectContainer.addEventListener('click', function () {
            isSelected = !isSelected;
            if (isSelected) {
                count ++;
                if (count < 4) {
                    isNoticeSelectContainer.classList.add('activeSelection');
                    selectNotice(boardNo);
                } else {
                    alert('최대 공지 수를 넘겼습니다!')
                    isSelected = !isSelected;
                    console.log("현재 카운트 : " + count)
                    count --;
                }

            } else {
                isNoticeSelectContainer.classList.remove('activeSelection');
                count --;
                deleteNotice(boardNo);

            }
        })
    });
})

function deleteBoard(boardNo, trBoardNo) {
    trBoardNo.remove();
    fetch(`/admin/board/delete?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            console.log('응답 성공!')
            if (response.redirected) {
                console.log('redirect 시도!')
                window.location.href = "/board";
            } else {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 형태로 응답받기
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error + '에러남!');
        });
}

function selectNotice(boardNo) {
    fetch(`/admin/notice/insert?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            console.log('응답 성공!')
            if (response.redirected) {
                console.log('redirect 시도!')
            } else {
                throw new Error('Network response was not ok');
            }
            // return response.json(); // JSON 형태로 응답받기
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error + '에러남!');
        });
}

function deleteNotice(boardNo) {
    fetch(`/admin/notice/delete?boardNo=${boardNo}`, {
        method: 'GET', // GET, POST, PUT, DELETE 중 선택
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            console.log('응답 성공!')
            if (response.redirected) {
                throw new Error('Network response was not ok');
            }
            // return response.json(); // JSON 형태로 응답 받기
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error + '에러남!');
        });
}
