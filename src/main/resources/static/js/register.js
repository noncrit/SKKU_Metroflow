const disableSpacebar = (event) => {
    if (event.code === "Space") {
        event.preventDefault();
    }
};

const disableKorean = (event) => {
    const value = event.target.value;
    if (/[\u3131-\uD79D]/.test(value)) {
        // 한글이 포함되어 있다면 제거
        event.target.value = value.replace(/[\u3131-\uD79D]/g, '');
    }
};

window.addEventListener('load', function () {
    const inputFields = document.getElementsByTagName("input");
    const nickname = document.getElementById('nickname');

    Array.from(inputFields).forEach(input => {
        input.addEventListener('keydown', disableSpacebar);

        if (nickname !== input) {
            input.addEventListener('input', disableKorean);
        }
    });
});


let selectedImage = null;
let selectedImageSrc = null;
// 예제 사용
function selectImage(imgElement) {
    // 기존 선택된 이미지가 있다면 'selected' 클래스를 제거합니다.
    // console.log(imgElement.src);
    if (selectedImage) {
        selectedImage.classList.remove('selected');
    }
    // 클릭한 이미지를 선택된 이미지로 설정하고 'selected' 클래스를 추가합니다.

    imgElement.classList.add('selected');
    selectedImage = imgElement;
    selectedImageSrc = imgElement.src;
    // 프로필 이미지를 클릭한 이미지로 업데이트합니다.
    document.getElementById('selectedProfile').src = imgElement.src;
    document.getElementById('imagePath').value = selectedImageSrc;
    // console.log("후" + selectedImageSrc);
    // 모달을 닫습니다.
    hideModal();
}


function showModal() {
    document.getElementById('imageModal').style.display = 'block';
}

function hideModal() {
    document.getElementById('imageModal').style.display = 'none';
}

// 폼 제출 이벤트 리스너
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('form').addEventListener('submit', function(event) {
        // event.preventDefault(); // 기본 제출 동작 방지

        // console.log("폼 제출 시 selectedImageSrc: " + selectedImageSrc);
        if (selectedImageSrc) {
            // console.log("선택된 이미지: " + selectedImageSrc);
            document.getElementById('imagePath').value = selectedImageSrc;
        } else {
            // console.log('선택된 이미지 없음, 기본값 사용');
            document.getElementById('imagePath').value = '/images/img_4.png'; // 기본값 설정
        }

        // 추가 로직이 있다면 여기서 수행

        // 수동으로 폼 제출하고 싶으면 아래 주석 해제
        // event.target.submit();
    });
});
