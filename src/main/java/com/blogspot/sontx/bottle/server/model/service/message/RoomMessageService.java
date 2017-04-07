package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;

public interface RoomMessageService {
    RoomMessage postMessage(RoomMessage message, AuthData authData);
}
