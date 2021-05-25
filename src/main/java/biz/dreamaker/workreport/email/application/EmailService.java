package biz.dreamaker.workreport.email.application;

public interface EmailService {

    void sendSimpleMessage(String to,
        String subject,
        String text);

}
