import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
// import  '../../common/css/Login.css';
import google_login from '../../common/img/google_login.png';



function LoginPage() {


    useEffect(() => {
        const form = document.getElementById('login-form');
        const handleSubmit = async (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    memId: username,
                    memPassword: password
                })
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('accessToken', data.accessToken);
                localStorage.setItem('loginMethod', 'custom'); // 로그인 방법 저장
                window.location.href = '/'; // 로그인 성공 후 메인 페이지로 이동
            } else {
                alert('로그인 실패');
            }
        };

        form.addEventListener('submit', handleSubmit);

        return () => {
            form.removeEventListener('submit', handleSubmit);
        };
    }, []);











    return (

        <>

            <div className="login-container">
                <div id="login-header">
                    <h2>로그인</h2>
                </div>
                {/* <!-- 구글로 시작하기 버튼 --> */}

                <form id="login-form">
                    <div className='wrap'>
                        <div className="box1">
                            <div className="box2">
                                <label for="username">아이디:</label>
                            </div>
                            <div className="box3">
                                <input type="text" id="username" name="username" required />
                            </div>
                        </div>
                        <div className="box">
                            <div className="box2">
                                <label for="password">비밀번호:</label>
                            </div>
                            <div className="box3">
                                <input type="password" id="password" name="password" required  />
                            </div>
                        </div>
                    </div>
                    <div className="box4">
                        {/* <!-- 로그인 버튼 --> */}
                        <input type="submit"  value="로그인" />
                        {/* <!-- 자바 or 서브및으로할지 추루 결정 --> */}
                    </div>
                </form>
                <div className="google_login">
                    <Link to="#"><img src={google_login} alt="구글로그인버튼" /></Link>
                </div>
                <div className="signup">
                    <div className="signupLink">
                        <Link to="/SignUp">회원가입</Link>
                    </div>
                    <div className="signup3">
                        <Link to="/FindIdPassword">아이디/비밀번호 찾기</Link>
                    </div>
                </div>
            </div>







        </>

    );


}




export default LoginPage;