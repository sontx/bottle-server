package com.blogspot.sontx.bottle.server.model.bean.event;

import lombok.Data;

@Data
public class RoomMessageChanged {
    private int id;
    private String state;
}
