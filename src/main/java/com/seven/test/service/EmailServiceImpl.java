package com.seven.test.service;

import com.seven.test.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final ThreadFactory THREAD_FACTORY = r -> {
        Thread mailThread = new Thread(r, "mail-sender-thread");
        mailThread.setUncaughtExceptionHandler((t, e) -> log.error(ValidationUtil.getRootCause(e).getMessage()));
        return mailThread;
    };

    private static final ExecutorService ES = Executors.newSingleThreadExecutor(THREAD_FACTORY);

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String text) {
        to = "<" + to + ">";
        //to = "<gpg.home@gmail.com>";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("<user@gmail.com>");
        message.setSubject("Credentials");
        message.setText(text);

        ES.execute(() -> {
            log.info("Try send email");
            emailSender.send(message);
        });
    }

    @PreDestroy
    public void tearDown() {
        ES.shutdownNow();
    }
}
