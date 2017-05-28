package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.bean.DeleteResult;
import com.blogspot.sontx.bottle.server.model.bean.chat.CreateChannelResult;

public interface ChatRepository {
    CreateChannelResult createChannel(String userId1, String userId2);

    void deleteExpiredChannels(long beforeMillis);

    DeleteResult deleteChannel(String channelId);
}
