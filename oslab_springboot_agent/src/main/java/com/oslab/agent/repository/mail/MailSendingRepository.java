package com.oslab.agent.repository.mail;

import com.oslab.agent.model.transfer.mailDto.SendInvitationMailDto;
import com.oslab.agent.model.transfer.mailDto.UpdateStateDto;
import com.oslab.agent.repository.mail.mapper.MailSendingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MailSendingRepository {

    private final MailSendingMapper mailSendingMapper;

    public void setPendingUser(SendInvitationMailDto sendInvitationMailDto){
        mailSendingMapper.setPendingUser(sendInvitationMailDto);
    }

    public boolean setPendingUserToContributor(UpdateStateDto updateStateDto){
        return mailSendingMapper.setPendingUserToContributor(updateStateDto);
    }

    public Long getUserIdFromClientEmail(String client_email){
        return mailSendingMapper.getUserIdFromClientEmail(client_email);
    }

}
