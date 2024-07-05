// 모달창 페이지 선택
const modalPage = document.querySelector(".modal_page");
// 모달창 닫기 버튼 선택
const withdrawExit = document.querySelector(".withdraw_exit");
// 모달창 확인 버튼 선택
const withdrawYes = document.querySelector(".withdraw_yes"); 

// 탈퇴하기 버튼 선택
const withdrawBtn = document.querySelector(".withdraw_btn")

withdrawBtn.addEventListener('click', () => {

// 비밀번호 입력 value 선택
const checkPassword = document.querySelector(".check_password").value;
// '비밀번호 재확인' 값 선택
const confirmPassword = document.querySelector(".confirm_password").value;

    // 비밀번호를 입력하지 않을시
    if(checkPassword === "" || confirmPassword === "" ){
        // 경고창 이벤트 발생
        alert("비밀번호를 입력해주세요")
    }
    // 비밀번호가 일치하지 않을시
    else if(checkPassword !== confirmPassword){
        // 경고창 이벤트 발생
        alert("비밀번호가 일치하지 않습니다")
    }
    else{
        modalPage.style.display = "flex"
    }


})

withdrawYes.addEventListener("click", (event) => {

    modalPage.style.display = "none"

})


// 모달 창 닫기 버튼 클릭 시 모달창 닫기
withdrawExit.addEventListener('click', function() {
    // 모달창의 display에 none 요소를 추가
    modalPage.style.display = "none";

    // 여기에 탈퇴 기능 추가하면 될 듯




});
























