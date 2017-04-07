package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomMessage extends Message {
    private int roomId;
}
