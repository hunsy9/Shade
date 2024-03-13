package com.oslab.agent.controller.mail;

import com.oslab.agent.model.transfer.mailDto.SendInvitationMailDto;
import com.oslab.agent.model.transfer.mailDto.VerifyAuthCodeDto;
import com.oslab.agent.service.mail.MailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MailSendingController {

    private final MailSendingService mailSendingService;

    @PostMapping("api/mail/sendAuthMail")
    public String sendAuthMail(@RequestParam("email") String clientEmail) throws Exception {
        return mailSendingService.sendAuthMail(clientEmail);
    }

    @PostMapping("api/mail/verifyCode")
    public ResponseEntity<Boolean> verifyAuthCode(@RequestBody VerifyAuthCodeDto verifyAuthCodeDto) throws Exception {
        boolean success = mailSendingService.verifyCode(verifyAuthCodeDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/mail/sendInvitationMail")
    public ResponseEntity<Boolean> sendInvitationMail(@RequestBody SendInvitationMailDto sendInvitationMailDto) throws Exception{
        Long user_id = mailSendingService.getUserIdFromClientEmail(sendInvitationMailDto.getClient_email());
        if(user_id!=null){
            sendInvitationMailDto.setUser_id(user_id);
            mailSendingService.sendInvitationMail(sendInvitationMailDto);
        }else{
            return ResponseEntity.status(400).body(false);
        }
        return ResponseEntity.ok().body(true);
    }


}
