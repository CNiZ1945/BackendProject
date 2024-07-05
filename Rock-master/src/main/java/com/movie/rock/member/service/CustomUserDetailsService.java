package com.movie.rock.member.service;

import com.movie.rock.member.data.MemberEntity;
import com.movie.rock.member.data.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 김승준 - 회원
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new UsernameNotFoundException(memId + " 라는 아이디를 찾을수 없습니다."));
        return new CustomUserDetails(memberEntity);
    }
}
