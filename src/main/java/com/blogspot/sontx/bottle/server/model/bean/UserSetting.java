package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public class UserSetting {
    private int currentRoomId;
    private Coordination currentLocation;
    private int messageId;
}
