package com.movie.rock.member.service;

import com.movie.rock.member.data.EmailVerificationEntity;
import com.movie.rock.member.data.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Random;

@Service
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository, JavaMailSender mailSender) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.mailSender = mailSender;
    }

    // 이메일 인증 토큰 생성 및 저장, 이메일 발송
    @Transactional
    public void sendVerificationEmail(String email) {
        String token = generateVerificationToken();
        EmailVerificationEntity verification = EmailVerificationEntity.builder()
                .email(email)
                .token(token)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .build();

        emailVerificationRepository.save(verification);
        sendEmailVerificationMessage(email, token);
    }

    // UUID를 사용해 고유 토큰 생성
    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    // 이메일 인증 메시지 발송
    private void sendEmailVerificationMessage(String email, String token) {
        try {
            String subject = "Rock 이메일 인증";
            String confirmationUrl = "http://localhost:8080/auth/verify-email?token=" + token;
            String message = "<p>안녕하세요,</p>" +
                    "<p>이메일을 인증하려면 아래 링크를 클릭하세요:</p>" +
                    "<a href=\"" + confirmationUrl + "\">이메일 인증</a>" +
                    "<p>감사합니다</p>";

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            try {
                helper.setFrom("sjk030221@gmail.com", "MovieRock@gmail.com");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Failed to set email sender", e);
            }

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email verification message", e);
        }
    }

    // 이메일 인증 확인
    @Transactional
    public boolean verifyEmail(String email, String token) {
        EmailVerificationEntity verification = emailVerificationRepository.findByEmailAndToken(email, token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or token"));

        if (verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        verification.setVerified(true);
        emailVerificationRepository.save(verification);
        return true;
    }

    // 이메일 인증 여부 확인
    @Transactional(readOnly = true)
    public boolean isEmailVerified(String email) {
        return emailVerificationRepository.findByEmailAndVerified(email, true).isPresent();
    }

    // 회원가입용 이메일 인증 코드 생성 및 발송
    @Transactional
    public void sendSignUpVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();
        EmailVerificationEntity verification = EmailVerificationEntity.builder()
                .email(email)
                .verificationCode(verificationCode)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .verified(false)
                .token(null)
                .build();

        emailVerificationRepository.save(verification);
        sendSignUpEmailVerificationMessage(email, verificationCode);
    }

    // 6자리 영문 + 숫자 난수 코드 생성
    private String generateVerificationCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // 회원가입 인증용 메시지 발송
    private void sendSignUpEmailVerificationMessage(String email, String verificationCode) {
        try {
            String subject = "Rock 회원가입 이메일 인증";
            String message = "<p>안녕하세요,</p>" +
                    "<p>회원가입을 완료하기 위한 인증 코드입니다:</p>" +
                    "<h2>" + verificationCode + "</h2>" +
                    "<p>이 코드는 10분 동안 유효합니다.</p>" +
                    "<p>감사합니다</p>";

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            try {
                helper.setFrom("sjk030221@gmail.com", "MovieRock@gmail.com");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Failed to set email sender", e);
            }

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email verification message", e);
        }
    }

    // 회원가입 이메일 인증 확인
    @Transactional
    public boolean verifySignUpEmail(String email, String verificationCode) {
        EmailVerificationEntity verification = emailVerificationRepository.findByEmailAndVerificationCode(email, verificationCode)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 또는 인증 코드입니다."));

        if (verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        verification.setVerified(true);
        emailVerificationRepository.save(verification);
        return true;
    }

    // 회원가입 이메일 인증 여부확인
    @Transactional(readOnly = true)
    public boolean isEmailVerifiedForSignUp(String email) {
        return emailVerificationRepository.findByEmailAndVerified(email, true).isPresent();
    }
}