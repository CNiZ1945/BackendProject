package com.movie.rock.member.controller;

import com.movie.rock.config.JwtUtil;
import com.movie.rock.member.data.*;
import com.movie.rock.member.service.CustomUserDetailsService;
import com.movie.rock.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// 김승준 - 회원

// 아찾 = 이메일 이름 || 비찾 = 아이디 이메일
// 회원 정보 찾기 번호로 아이디로 이름으로 이메일로 전화번호로
@RestController
@RequestMapping("/auth")
public class AuthController {


    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberService memberService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getMemId(), authRequestDTO.getMemPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("잘못된 자격 증명", e);
        }

        final String accessToken = jwtUtil.generateAccessToken(authRequestDTO.getMemId());
        final String refreshToken = jwtUtil.generateRefreshToken(authRequestDTO.getMemPassword());

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken, "custom"));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequestDTO signupRequestDTO) {

        if (!signupRequestDTO.getMemPassword().equals(signupRequestDTO.getMemPasswordCheck())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        // SignupRequest를 MemberDTO로 변환
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setMemId(signupRequestDTO.getMemId());

        memberDTO.setMemPassword(signupRequestDTO.getMemPassword());

        memberDTO.setMemEmail(signupRequestDTO.getMemEmail());

        memberDTO.setMemTel(signupRequestDTO.getMemTel());

        memberDTO.setMemGender(signupRequestDTO.getMemGender());

        memberDTO.setMemRole(RoleEnum.USER);
        
        memberDTO.setMemName(signupRequestDTO.getMemName());

        memberDTO.setMemBirth(signupRequestDTO.getMemBirth());

        try {
            memberService.registerNewMember(memberDTO);

            return ResponseEntity.ok("회원가입에 성공하였습니다.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 헤더 바디에 둘 memberinfo 정보
    @GetMapping("/memberinfo")
    public ResponseEntity<?> memberInfo(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않음");
        }

        String memId = authentication.getName();
        MemberEntity member = memberService.findbyMemId(memId);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾지 못했습니다.");
        }

        // 필요한 정보만 포함하는 DTO 생성
        MemberInfoDTO memberInfo = new MemberInfoDTO();

        memberInfo.setMemId(member.getMemId());

        memberInfo.setMemName(member.getMemName());

        memberInfo.setMemEmail(member.getMemEmail());

        memberInfo.setMemTel(member.getMemTel());

        memberInfo.setMemGender(member.getMemGender());

        memberInfo.setMemRole(member.getMemRole());

        return ResponseEntity.ok(memberInfo);
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateMember(@RequestBody UpdateMemberDTO updateDto, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않음");
        }

        String memId = authentication.getName();

        try {
            memberService.updateMember(memId, updateDto);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정 되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 중 오류 발생: " + e.getMessage());
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않음");
        }
        String memId = authentication.getName();
        try {
            memberService.delete(memId);
            return ResponseEntity.ok("탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생하였습니다.");
        }
    }

    // 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdRequestDTO requestDTO) {
        Optional<MemberEntity> member = memberService.findByMemId(requestDTO.getMemEmail(), requestDTO.getMemName());
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get().getMemId());
        } else {
            return ResponseEntity.status(404).body("회원정보를 찾지 못했습니다.");
        }
    }

    // 비밀번호 변경

    // 1. 비밀번호 찾기
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDTO request) {
        try {
            MemberEntity member = memberService.findMemberByUsernameAndEmail(request.getMemId(), request.getMemEmail());
            if (member == null) {
                return ResponseEntity.badRequest().body("사용자를 찾을수 없거나 이메일이 일치하지 않습니다.");
            }
            String token = UUID.randomUUID().toString();
            memberService.createPasswordResetTokenForMember(member, token);
            memberService.sendPasswordResetEmail(member.getMemEmail(), token);
            return ResponseEntity.ok().body("비밀번호 재설정 이메일 발송완료");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("요청을 처리하는 동안 오류가 발생했습니다");
        }
    }

    // 2. 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody UpdatePasswordDTO updatePasswordDto) {
        try {
            memberService.resetPassword(token, updatePasswordDto.getMemNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호를 재설정하는 동안 오류가 발생했습니다");
        }
    }






    // 토큰 리프래쉬
//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
//        try {
//            String refreshToken = request.getRefreshToken();
//            String memId = jwtUtil.extractMemberId(refreshToken);
//
//            if (memId != null && jwtUtil.isTokenValid(refreshToken, memId)) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(memId);
//                // 여기를 수정합니다
//                String newAccessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
//                // 선택적: 리프레시 토큰 재발급 (보안 강화를 위해)
//                String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
//
//                return ResponseEntity.ok(new AuthResponseDTO(newAccessToken, newRefreshToken, "custom"));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing token");
//        }
//    }

}
