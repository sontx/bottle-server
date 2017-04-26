package com.blogspot.sontx.bottle.server.model.bean.chat;

import lombok.Data;

@Data
public class ChannelDetail {
    private String lastMessage;
    private String messageType;
    private long timestamp;
}
