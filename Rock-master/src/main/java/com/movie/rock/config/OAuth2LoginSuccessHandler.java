package com.movie.rock.config;

import com.movie.rock.member.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 인증된 사용자 정보 커스텀
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 서비스를 통해 토큰 생성
        String token = customOAuth2UserService.generateToken(oAuth2User);

        // 클라이언트에 JWT 토큰과 로그인 방식을 전달 (URL 파라미터로)
        getRedirectStrategy().sendRedirect(request, response, "/?token=" + token + "&loginMethod=google");
    }
}