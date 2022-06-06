package com.healthtrip.travelcare.repository.dto.request;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MailRequest {
    private String from;
    private String to;
    private String subject;
    private String content;

    @Builder
    public MailRequest(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public void setTo(String email) {
        this.to = email;
    }

    public SendEmailRequest toSendRequestDto() {
        Destination destination = new Destination().withToAddresses(this.to);
        Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body()
                        .withHtml(createContent(this.content)));
        // 바디는 html식
        return new SendEmailRequest()
                .withSource(this.from)
                .withDestination(destination)
                .withMessage(message);
    }
    private Content createContent(String text) {
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}
