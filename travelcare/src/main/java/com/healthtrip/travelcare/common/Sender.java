package com.healthtrip.travelcare.common;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.healthtrip.travelcare.repository.dto.request.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public void send(MailRequest mailRequest){
        MailRequest senderDto = mailRequest.builder()
                .from(mailRequest.getFrom())
                .to(mailRequest.getTo())
                .subject(mailRequest.getSubject())
                .content(mailRequest.getContent())
                .build();
        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(senderDto.toSendRequestDto());

        if(sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            System.out.println("[AWS SES] 메일전송완료 => " + mailRequest.getTo());
        }else {
//            System.out.println(("[AWS SES] 메일전송 중 에러가 발생했습니다: {}"), sendEmailResult.getSdkResponseMetadata().toString());
            System.out.println("발송실패 대상자: " + mailRequest.getTo() + " / subject: " + mailRequest.getSubject());
        }

    }

}
