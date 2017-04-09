package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public abstract class Message {
    private int id;
    private String text;
    private String mediaUrl;
    private long timestamp;
    private PublicProfile owner;
}
