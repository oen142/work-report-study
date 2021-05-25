package biz.dreamaker.workreport.email.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailObject {

    @Email
    @NotNull
    @Size(min = 1, message = "Please , set an email address to send the message to it")
    private String to;
    private String recipientName;
    private String subject;
    private String text;
    private String senderName;
    private String templateEngine;

}
