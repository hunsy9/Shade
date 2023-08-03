package com.oslab.agent.repository.mail.mapper;

import com.oslab.agent.model.transfer.mailDto.SendInvitationMailDto;
import com.oslab.agent.model.transfer.mailDto.UpdateStateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailSendingMapper {
    void setPendingUser(SendInvitationMailDto sendInvitationMailDto);

    boolean setPendingUserToContributor(UpdateStateDto updateStateDto);
    Long getUserIdFromClientEmail(String client_email);
}
