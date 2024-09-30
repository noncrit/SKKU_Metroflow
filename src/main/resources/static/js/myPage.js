function changeProfilePic() {
    // 프로필 사진 변경 후 DB로 전송하도록 설정 필요
    alert('프로필 변경 완료하였습니다.');
}

// 비밀번호 변경 처리
document.getElementById('profileForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
    } else {
        // 비밀번호가 일치하면 DB에 새로운 비밀번호 업데이트 설정 필요
        alert('비밀번호 변경 완료되었습니다.');
    }
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
