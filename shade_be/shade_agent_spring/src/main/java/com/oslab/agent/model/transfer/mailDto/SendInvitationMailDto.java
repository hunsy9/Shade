package com.oslab.agent.model.transfer.mailDto;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendInvitationMailDto {
    private Long org_id;
    @Nullable
    private Long user_id;
    private String org_name;
    private String client_email;
}
