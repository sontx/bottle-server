package com.blogspot.sontx.bottle.server.model.service.chat;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.DeleteResult;
import com.blogspot.sontx.bottle.server.model.bean.chat.CreateChannelResult;

public interface ChatService {
    CreateChannelResult createChannel(String buddyId, AuthData authData);

    DeleteResult deleteChannel(String channelId, AuthData authData);
}
