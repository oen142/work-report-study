package biz.dreamaker.workreport.email.ui;

import biz.dreamaker.workreport.email.application.EmailService;
import biz.dreamaker.workreport.email.dto.MailObject;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);

        //Email with template
        props = new HashMap<>();
        props.put("headerText", "Send Email Using Text Template");
        props.put("messageLabel", "Template Parameter");
        props.put("additionalInfo",
            "The parameter value will be added to the following message template:<br>" +
                "<b>This is the test email template for your email:<br>'Template Parameter'</b>"
        );
        labels.put("sendTemplate", props);

        //Email with attachment
        props = new HashMap<>();
        props.put("headerText", "Send Email With Attachment");
        props.put("messageLabel", "Message");
        props.put("additionalInfo",
            "To make sure that you send an attachment with this email, change the value for the 'attachment.invoice' in the application.properties file to the path to the attachment.");
        labels.put("sendAttachment", props);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER')")
    @PostMapping("/api/send")
    public ResponseEntity<String> createMail(
        @Valid @RequestBody MailObject mailObject
    ) {

        emailService.sendSimpleMessage(mailObject.getTo(),
            mailObject.getSubject(), mailObject.getText());

        return ResponseEntity.ok().body("success");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER')")
    @PostMapping("/api/send/template")
    public ResponseEntity<String> createSimpleTemplate(
        @Valid @RequestBody MailObject mailObject
    ) {

        emailService.sendSimpleMessageUsingTemplate(mailObject.getTo(),
            mailObject.getSubject(), "드림메이커" , "테스트작업자" , "2020-02-10");

        return ResponseEntity.ok().body("success");
    }
}
