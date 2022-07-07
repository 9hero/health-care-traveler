package com.healthtrip.travelcare.common;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.healthtrip.travelcare.repository.dto.request.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class Sender {

    private final JavaMailSender javaMailSender;
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public void awsSender(MailRequest mailRequest){
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

    @Value("${spring.mail.username}")
    private String naverFrom;

    @Async
    public void naverSender(MailRequest mailRequest) throws RuntimeException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        try{
            helper.setFrom(naverFrom);
            helper.setTo(mailRequest.getTo());
            helper.setSubject(mailRequest.getSubject());
            helper.setText(mailRequest.getContent());
            javaMailSender.send(message);
            System.out.println("메일 보내기 메소드"+helper);
        }catch (SendFailedException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        catch (MessagingException e){
            System.out.println("naver email error");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
