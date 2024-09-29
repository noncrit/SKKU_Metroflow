document.addEventListener('DOMContentLoaded', function () {
    let thumbsUp = document.getElementById("thumbsUp").value.toLowerCase() === "true";
    let thumbsDown = document.getElementById("thumbsDown").value.toLowerCase() === "true";

    let hiddenUp = document.getElementById('thumbsUp').value.toLowerCase() === "true";
    let hiddenDown = document.getElementById('thumbsDown').value.toLowerCase() === "true";
    const thumbsUpContainer = document.getElementById('thumbsUpContainer');
    const thumbsDownContainer = document.getElementById('thumbsDownContainer');
    let thumbsUpCount = document.getElementById('thumbsUpCount');
    let thumbsDownCount = document.getElementById('thumbsDownCount');

    if (thumbsUp === true) {
        thumbsUpContainer.classList.add('thumbsUpActive');
    } else if (thumbsDown === true) {
        thumbsDownContainer.classList.add('thumbsDownActive')
    }

    thumbsUpContainer.addEventListener('click', function () {
        thumbsUp = !thumbsUp;

        if (thumbsUp === true && thumbsDown === true) {
            thumbsDown = false;
            thumbsUpContainer.classList.add('thumbsUpActive'); // 강조 효과
            thumbsUpCount.text = String(parseInt(thumbsUpCount.text) + 1); // 카운트 증가
            thumbsUpCount.value = String(parseInt(thumbsUpCount.value) + 1); // 카운트 증가
            thumbsDownContainer.classList.remove('thumbsDownActive');
            thumbsDownCount.text = String(parseInt(thumbsDownCount.text) - 1); // 카운트 감소
            thumbsDownCount.value = String(parseInt(thumbsDownCount.value) - 1); // 카운트 감소
        } else if (thumbsUp === true) {
            thumbsUpContainer.classList.add('thumbsUpActive'); // 강조 효과
            thumbsUpCount.text = String(parseInt(thumbsUpCount.text) + 1); // 카운트 증가
            thumbsUpCount.value = String(parseInt(thumbsUpCount.value) + 1); // 카운트 증가
        } else {
            thumbsUpContainer.classList.remove('thumbsUpActive'); // 강조 효과
            thumbsUpCount.text = String(parseInt(thumbsUpCount.text) - 1); // 카운트 감소
            thumbsUpCount.value = String(parseInt(thumbsUpCount.value) - 1); // 카운트 감소
        }
        hiddenUp = thumbsUp;
        hiddenDown = thumbsDown;
    });

    thumbsDownContainer.addEventListener('click', function () {
        thumbsDown = !thumbsDown;
        if (thumbsDown === true && thumbsUp === true) {
            thumbsUp = false;
            thumbsUpContainer.classList.remove('thumbsUpActive'); // 강조 효과
            thumbsUpCount.text = String(parseInt(thumbsUpCount.text) - 1); // 카운트 감소
            thumbsUpCount.value = String(parseInt(thumbsUpCount.value) - 1); // 카운트 감소
            thumbsDownContainer.classList.add('thumbsDownActive');
            thumbsDownCount.text = String(parseInt(thumbsDownCount.text) + 1); // 카운트 증가
            thumbsDownCount.value = String(parseInt(thumbsDownCount.value) + 1); // 카운트 증가
        } else if (thumbsDown === true) {
            thumbsDownContainer.classList.add('thumbsDownActive'); // 강조 효과
            thumbsDownCount.text = String(parseInt(thumbsDownCount.text) + 1); // 카운트 증가
            thumbsDownCount.value = String(parseInt(thumbsDownCount.value) + 1); // 카운트 증가
        } else {
            thumbsDownContainer.classList.remove('thumbsDownActive'); // 강조 효과
            thumbsDownCount.text = String(parseInt(thumbsDownCount.text) - 1); // 카운트 감소
            thumbsDownCount.value = String(parseInt(thumbsDownCount.value) - 1); // 카운트 감소
        }
        hiddenUp = thumbsUp;
        hiddenDown = thumbsDown;
    })
    const aLink = document.querySelectorAll('a');
    const boardNo = document.getElementById('boardNo').value;
    const priorThumbsUp = document.getElementById('priorThumbsUp').value.toLowerCase() === "true";
    const priorThumbsDown = document.getElementById('priorThumbsDown').value.toLowerCase() === "true";
    aLink.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            location.href = "/board/recommendation?url=" + link.href.substring(21) + "&boardNo=" + boardNo + "&isThumbsUp="
                + hiddenUp + "&isThumbsDown=" + hiddenDown + "&priorThumbsUp=" + priorThumbsUp + "&priorThumbsDown=" + priorThumbsDown;
        });
    });
    const buttonLink = document.querySelectorAll('button');
    buttonLink.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            location.href = "/board/recommendation?url=" + link.form.action.substring(21) + "&boardNo=" + boardNo + "&isThumbsUp="
                + hiddenUp + "&isThumbsDown=" + hiddenDown + "&priorThumbsUp=" + priorThumbsUp + "&priorThumbsDown=" + priorThumbsDown;
        });
    });
    const deleteButton = document.querySelector('.btn-delete');

    deleteButton.addEventListener('click', function (event) {
        const confirmation = confirm('정말로 삭제하시겠습니까?');

        if (!confirmation) {
            // 사용자가 취소를 선택하면 삭제하지 않음
            event.preventDefault();
            return "stop";
        } else {
            // 삭제 진행 코드 추가 필요
            alert('삭제가 완료되었습니다.');
        }
    });
})
