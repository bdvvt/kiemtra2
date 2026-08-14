package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.models.services.IMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendOtpMail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã xác minh tài khoản");
        message.setText("Mã OTP kích hoạt tài khoản của bạn là: " + otp + ". Mã có hiệu lực trong 5 phút.");
        mailSender.send(message);

    }

}
