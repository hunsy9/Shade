package com.oslab.agent.service.mail;

import com.oslab.agent.model.transfer.mailDto.SendAuthMailDto;
import com.oslab.agent.model.transfer.mailDto.SendInvitationMailDto;
import com.oslab.agent.model.transfer.mailDto.VerifyAuthCodeDto;
import com.oslab.agent.repository.mail.MailSendingRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailSendingService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String,String> redisTemplate;
    private final TemplateEngine htmlTemplateEngine;

    private final MailSendingRepository mailSendingRepository;

    private int generateCode(){
        Random random = new Random();
        int randomCode = random.nextInt(899999)+100000;
        return randomCode;
    }

    private void storeCodeToRedis(String userId, int code) throws Exception{
        redisTemplate.opsForValue().set(userId, String.valueOf(code), 300000L, TimeUnit.MILLISECONDS);
    }

    public Long getUserIdFromClientEmail(String clientEmail) throws SQLException {
        return mailSendingRepository.getUserIdFromClientEmail(clientEmail);
    }

    private void setPendingUser(SendInvitationMailDto sendInvitationMailDto){
        mailSendingRepository.setPendingUser(sendInvitationMailDto);
    }

    public String sendAuthMail(String clientEmail) throws Exception {
        int code = generateCode();
        Context context = new Context();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        InternetAddress from = new InternetAddress("juniper0917@gmail.com", "SSH DESKTOP");
        InternetAddress to = new InternetAddress(clientEmail);

        context.setVariable("text", code);

        String htmlTemplate = htmlTemplateEngine.process("mail/invitationMail", context);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject("[SSH DESKTOP] Verify Code From SSH DESKTOP");
        messageHelper.setText(htmlTemplate, true);
        javaMailSender.send(mimeMessage);

        String mailCodeKey = String.valueOf(UUID.randomUUID());
        String mailRedisKey = "MailCodeBuffer:" + mailCodeKey;
        storeCodeToRedis(mailRedisKey, code);
        return mailCodeKey;
    }

    public boolean verifyCode(VerifyAuthCodeDto verifyAuthCodeDto) throws Exception {
        String mailKey ="MailCodeBuffer:"+verifyAuthCodeDto.getMailKey();
        String code = verifyAuthCodeDto.getCode();

        boolean hasKey = redisTemplate.hasKey(mailKey);
        if(hasKey){
            boolean authSuccess = redisTemplate.opsForValue().get(mailKey) == code;
            if(authSuccess){
                return true;
            }
        }
        return false;
    }

    public void sendInvitationMail(SendInvitationMailDto sendInvitationMailDto) throws Exception {
        Context context = new Context();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");

        InternetAddress from = new InternetAddress("juniper0917@gmail.com", "SSH DESKTOP");
        System.out.println(sendInvitationMailDto.getClient_email());
        InternetAddress to = new InternetAddress(sendInvitationMailDto.getClient_email());
        String upperCase_org_name = sendInvitationMailDto.getOrg_name().toUpperCase();
        String href = "http://localhost:8081/api/contributor/add?org_id="+ sendInvitationMailDto.getOrg_id() +"&user_id="+sendInvitationMailDto.getUser_id()+"&contributor_email="+sendInvitationMailDto.getClient_email();
        String header = "You has been invited to join the " + upperCase_org_name + "_Organization";
        context.setVariable("header", header);
        context.setVariable("href", href);
        context.setVariable("join", "JOIN "+upperCase_org_name);
        String htmlTemplate = htmlTemplateEngine.process("mail/invitationMail", context);
        String subject = "[SSH DESKTOP] " + header;
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(htmlTemplate, true);
        javaMailSender.send(mimeMessage);
        setPendingUser(sendInvitationMailDto);
    }

}
