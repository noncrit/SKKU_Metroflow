// 프로필 사진 변경
function changeProfilePic() {
    document.getElementById('profileImage').click();
}

document.getElementById('profileImage').addEventListener('change', function () {
    const formData = new FormData();
    formData.append('profileImage', this.files[0]);

    fetch('/updateProfileImage', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('프로필 싲ㄴ 변경 실패,');
            }
        })
        .then(data => {
            document.getElementById('profilePic').src = data.newImagePath;
            alert('프로필 사진이 변경되었습니다.');
        })
        .catch(error => {
            console.error(error);
            alert('오류가 발생했습니다.');
        });
});

// // 비밀번호 변경 처리
// function changePassword(event) {
//     event.preventDefault();
//
//     const userId = jeong;
//     const currentPassword = document.getElementById('currentPassword').value;
//     const newPassword = document.getElementById('newPassword').value;
//     const confirmPassword = document.getElementById('confirmPassword').value;
//
//     if (newPassword !== confirmPassword) {
//         alert("New password do not match!");
//         return;
//     }
//
//     fetch(`/myPage/password?userId=${userId}&currentPassword=${currentPassword}&newPassword=${newPassword}`,{
//         method: 'PUT',
//     })
//         .then(response => response.text())
//         .then(data => {
//             alert(data);
//             document.getElementById('profileForm').reset();
//         })
//         .catch(error => console.error('Error:', error));
//
// }

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
