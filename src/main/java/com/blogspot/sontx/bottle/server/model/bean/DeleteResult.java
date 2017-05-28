package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public class DeleteResult {
    private String channelId;
    private boolean deleted;
}
