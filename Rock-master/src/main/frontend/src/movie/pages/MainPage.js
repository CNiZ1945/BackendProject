import React, { useEffect, useRef } from 'react';


// component
import RankingSlide from '../components/RankingSlide';
import RecommendReview from '../components/RecommendReview';
import RecommendStar from '../components/RecommendStar';
import UpdatedMovie from '../components/UpdatedMovie';


// css
import "../../common/css/MainPage.css";



function MainPage() {




    const initializedRef = useRef(false);

    useEffect(() => {
        if (!initializedRef.current) {
            initializedRef.current = true;

            const urlParams = new URLSearchParams(window.location.search);
            const token = urlParams.get('token');
            const loginMethod = urlParams.get('loginMethod');
            if (token && loginMethod) {
                localStorage.setItem('accessToken', token);
                localStorage.setItem('loginMethod', loginMethod);
                window.history.replaceState({}, document.title, "/"); // URL 클리닝
            }

            fetchMemberInfo();
        }
    }, []);

    async function fetchMemberInfo() {
        try {
            const accessToken = localStorage.getItem('accessToken');
            // const loginMethod = localStorage.getItem('loginMethod');
            let url = '/auth/memberinfo';

            // 로그인 방식에 따라 엔드포인트 선택
            // if (loginMethod === 'google') {
            //     url = '/auth/google-memberinfo';
            // }

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                }
            });

            // if (response.ok) {
            //     const memberInfo = await response.json();
            //     document.getElementById('member-info').innerText = '사용자 정보: ' + JSON.stringify(memberInfo);
            // } else {
            //     document.getElementById('member-info').innerText = '사용자 정보를 가져오지 못했습니다.';
            // }
        } catch (error) {
            console.error('Error fetching member info:', error);
            document.getElementById('member-info').innerText = '오류가 발생했습니다.';
        }
    }







    return (

        <>

            {/* <!-- ============================ 영화 랭킹 ============================= --> */}
            <div className="ranking">
                < RankingSlide />
            </div>

            {/* <!-- ============================ 영화 랭킹 ============================= --> */}
            {/* <!-- ============================ 평점 추천 ============================= --> */}
            <div className="star_recommend">
                <div className="MainPageListHead">
                    <h2>영화 별점 추천</h2>
                </div>
                < RecommendReview />
            </div>
            {/* <!-- ============================ 평점 추천 ============================= --> */}
            {/* <!-- ============================ 랭킹(리뷰 -> 평점) ============================= --> */}
            <div className="review_recommend">
                <div className="MainPageListHead" >
                    <h2>
                        영화 리뷰 추천
                    </h2>
                </div>
                <RecommendStar />
            </div>
            {/* <!-- ============================ 랭킹(리뷰 -> 평점) ============================= --> */}
            <div className="movie_upload">
                <div className="MainPageListHead" >
                    <h2>
                        최신 업로드된 영화
                    </h2>
                </div>
                <UpdatedMovie />
            </div>







        </>






    );
}

























export default MainPage;