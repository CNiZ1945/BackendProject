package com.movie.rock.member.service;

import com.movie.rock.member.data.MemberEntity;
import com.movie.rock.member.data.MemberRepository;
import com.movie.rock.config.JwtUtil;
import com.movie.rock.member.data.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        Optional<MemberEntity> existingMember = memberRepository.findByMemEmail(email);

        if (!existingMember.isPresent()) {
            MemberEntity newMember = MemberEntity.builder()
                    .memId(oAuth2User.getAttribute("sub"))
                    .memEmail(email)
                    .memName(oAuth2User.getAttribute("name"))
                    .memPassword("구글사용자") // Password is not needed for OAuth2
                    .memGender("구글사용자") // Default value for gender
                    .memTel("구글사용자") // Default value for telephone number
                    .memBirth(LocalDate.now()) // Default value for birth date
                    .memRole(RoleEnum.USER)
                    .build();
            memberRepository.save(newMember);
        }

        return oAuth2User;
    }

    public String generateToken(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        Optional<MemberEntity> member = memberRepository.findByMemEmail(email);
        if (member.isPresent()) {
            return jwtUtil.generateAccessToken(member.get().getMemId());
        }
        return null;
    }
}
