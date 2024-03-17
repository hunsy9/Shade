package com.oslab.agent.configuration.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String type;
    private String sender;
    private String receiver;
    private String data;

    public void newConnect(){
        this.type = "new";
    }

    public void closeConnect(){
        this.type = "close";
    }
}
