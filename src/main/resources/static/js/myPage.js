document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("profileModal");
    const openModalButton = document.getElementById("openModalButton");
    const closeModal = document.querySelector(".close");
    const profilePic = document.getElementById("profilePic");
    const selectableImages = document.querySelectorAll(".selectable-image");
    const selectedProfilePicInput = document.getElementById("selectedProfilePic");


    // 모달 열기
    openModalButton.addEventListener("click", function () {
        modal.style.display = "block";
    });

    // 모달 닫기
    closeModal.addEventListener("click", function () {
        modal.style.display = "none";
    });

    // 이미지 선택
    selectableImages.forEach(function (image) {
        image.addEventListener("click", function () {
            selectableImages.forEach(img => img.classList.remove("selected"));
            image.classList.add("selected");
            profilePic.src = image.src;  // 선택한 이미지를 프로필로 미리보기
            selectedProfilePicInput.value = image.src;  // 선택한 이미지 경로를 hidden input에 저장
        });
    });

    // 선택한 이미지 저장
    document.getElementById("changeProfilePic").addEventListener("click", function () {
        modal.style.display = "none";  // 모달 닫기
    });
});


// 회원 탈퇴 확인 처리
document.getElementById('deleteAccount').addEventListener('click', function(event) {
    event.preventDefault();
    const confirmation = confirm("정말로 회원 탈퇴를 하시겠습니까?");

    if (confirmation) {
        // 회원 탈퇴 처리를 하는 코드 (DB 관련 작업 또는 서버로 탈퇴 요청 설정 필요)
        alert("회원 탈퇴가 완료되었습니다.");
    } else {
        alert("회원 탈퇴가 취소되었습니다.");
    }
});
