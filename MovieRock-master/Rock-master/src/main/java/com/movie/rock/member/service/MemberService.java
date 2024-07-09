package com.movie.rock.member.service;

import com.movie.rock.member.data.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// 김승준 - 회원
@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 패스워드 암호화를 위한 빈

    private MemberDTO memberDTO;

    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;

    // 회원가입 로직
    public void registerNewMember(MemberDTO memberDTO) throws Exception {
        if (memberRepository.findByMemId(memberDTO.getMemId()).isPresent()) {
            throw new Exception("아이디가 이미 사용중입니다.");
        }
        if(memberRepository.findByMemEmail(memberDTO.getMemEmail()).isPresent()) {
            throw new Exception("이메일이 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(memberDTO.getMemPassword());

        memberRepository.save(memberDTO.toEntity(encodedPassword));
    }

    // 회원탈퇴 로직
    public void delete(String memId) throws Exception {
        Optional<MemberEntity> member = memberRepository.findByMemId(memId);
        if(member.isPresent()) {
            memberRepository.delete(member.get());
        } else {
            throw new Exception("회원 정보를 찾을 수 없습니다");
        }
    }

    // 회원정보 수정 로직
    public void updateMember(String memId, UpdateMemberDTO updateDto) {
        MemberEntity member = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        // 비밀번호 변경
        if (updateDto.getMemNewPassword() != null && !updateDto.getMemNewPassword().isEmpty()) {
            if (!updateDto.getMemNewPassword().equals(updateDto.getMemNewPasswordCheck())) {
                throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            }
            member.updatePassword(passwordEncoder.encode(updateDto.getMemNewPassword()));
        }

        // 이메일 변경
        if (updateDto.getMemNewEmail() != null && !updateDto.getMemNewEmail().isEmpty()) {
            member.updateEmail(updateDto.getMemNewEmail());
        }

        // 전화번호 변경
        if (updateDto.getMemNewTel() != null && !updateDto.getMemNewTel().isEmpty()) {
            member.updateTel(updateDto.getMemNewTel());
        }

        memberRepository.save(member);
    }

    // 아이디 찾기 로직
    public Optional<MemberEntity> findByMemId(String memEmail, String memName) {
        return memberRepository.findByMemEmailAndMemName(memEmail, memName);
    }

    // 헤더에 정보를 넣기 위한 메서드
    public MemberEntity findbyMemId(String memId) {
        return memberRepository.findByMemId(memId).orElse(null);
    }







    // -------------------------------------------------------

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;


    @Transactional
    public void createPasswordResetTokenForMember(MemberEntity member, String token) {


        // 기존 토큰 삭제
        passwordResetTokenRepository.deleteAllByMember(member);

        // 새 토큰 생성
        PasswordResetTokenDTO myToken = PasswordResetTokenDTO.builder()
                .token(token)
                .member(member)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .build();

        // 토큰 저장
        try {
            passwordResetTokenRepository.saveAndFlush(myToken);

        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException("Failed to create password reset token. Please try again.", e);
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetTokenDTO resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        MemberEntity member = resetToken.getMember();
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        passwordResetTokenRepository.delete(resetToken);

    }


    public void sendPasswordResetEmail(String email, String token) {
        try {
            String subject = "Rock 비밀번호 재설정";
            String confirmationUrl = "http://localhost:8080/auth/reset-password?token=" + token;
            String message = "<p>안녕하세요,</p>" +
                    "<p>비밀번호를 재설정하려면 아래 링크를 클릭하세요:</p>" +
                    "<a href=\"" + confirmationUrl + "\">비밀번호 재설정</a>" +
                    "<p>감사합니다</p>";

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true); // true indicates HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    public Optional<MemberEntity> getMemberByPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .map(PasswordResetTokenDTO::getMember);
    }

    public void updatePassword(String memId, UpdatePasswordDTO updatePasswordDto) {
        MemberEntity member = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!updatePasswordDto.getMemNewPassword().equals(updatePasswordDto.getMemNewPasswordCheck())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder.encode(updatePasswordDto.getMemNewPassword()));
        memberRepository.save(member);
    }

    public MemberEntity findMemberByUsernameAndEmail(String username, String email) {
        return memberRepository.findByMemIdAndMemEmail(username, email)
                .orElse(null);
    }

    public Optional<MemberEntity> findByEmail(String email) {
        return memberRepository.findByMemEmail(email);
    }





}
