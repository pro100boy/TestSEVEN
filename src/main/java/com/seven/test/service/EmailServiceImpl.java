package com.seven.test.service;

import com.seven.test.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String text) {
        to = "<" + to + ">";
        //to = "<gpg.home@gmail.com>";
        log.info("Try send email to: " + to);
        try {
            //emailSender.testConnection();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("<user@gmail.com>");
            message.setSubject("Credentials");
            message.setText(text);
            emailSender.send(message);
            log.info("Email was send to: " + to);
        } catch (Exception e) {
            log.error(ValidationUtil.getRootCause(e).getMessage());
        }
    }
}
