package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;

import java.util.List;

public interface RoomMessageService {
    RoomMessage postMessage(int roomId, RoomMessage message, AuthData authData);

    List<RoomMessage> getMessages(int roomId, int page, int pageSize);

    RoomMessage updateMessage(int messageId, RoomMessage message, AuthData authData);

    RoomMessage getMessage(int messageId);

    RoomMessage deleteMessage(int messageId, AuthData authData);
}
