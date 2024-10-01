    function deleteBoard(boardNo, boardListCount) {
    console.log(boardListCount);
        document.getElementById(boardListCount).remove();
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

    function insertNotice() {

    }

