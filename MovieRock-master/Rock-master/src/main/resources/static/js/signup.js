// form 확인 함수
function checkForm(){

// 회원 아이디 입력창 선택
const userID = document.querySelector("#username");
// 회원 비밀번호 입력창 선택
const userPW = document.querySelector("#password");

var regexPassword = /^(?=.*[a-z])(?=.*\d)[a-z\d]{8,12}$/;


    function checkUserID(){
        if(userID.value.length >=4 && userID.value.length <=12){
            return true;
        }
        else{
            alert("아이디는 4~12자리이어야 합니다.")
            userID.focus();
            return false;
        }
    }

    function checkPW(){
        

        
        
    // 비밀번호 유효성 검사
    // test(): 비교하여 문자가 포함되어 있으면 return true
    // changePassword가 조건을 만족하면
    if(regexPassword.test(newPassword)){
        return true
    }
    // 비밀번호가 조건에 만족하지 않다면
    else{
        alert("비밀번호는 영소문자, 숫자를 포함한 8~12자리로 만드셔야 합니다.")
        return false;
    }
    }

    if(checkUserID === true && checkPW === false){
        return true;
    }
    else{
        return false;
    }
}



