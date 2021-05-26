package biz.dreamaker.workreport.email.application;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String TEST_ADDRESS = "oen142123@dreamaker.biz";

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage simpleMailMessage;

    public EmailServiceImpl(JavaMailSender javaMailSender,
        SimpleMailMessage simpleMailMessage) {
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    @Async
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(TEST_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    @Async
    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {
        String text = String.format(Objects.requireNonNull(simpleMailMessage.getText()), templateModel);
        sendSimpleMessage(to, subject, text);
    }
}
