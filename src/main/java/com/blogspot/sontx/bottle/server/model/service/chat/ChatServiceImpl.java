package com.blogspot.sontx.bottle.server.model.service.chat;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.DeleteResult;
import com.blogspot.sontx.bottle.server.model.bean.chat.CreateChannelResult;
import com.blogspot.sontx.bottle.server.model.repository.ChatRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public CreateChannelResult createChannel(String buddyId, AuthData authData) {
        if (buddyId == null || authData == null)
            return null;
        String currentUserId = authData.getUid();
        if (currentUserId.equalsIgnoreCase(buddyId))
            return null;
        return chatRepository.createChannel(currentUserId, buddyId);
    }

    @Override
    public DeleteResult deleteChannel(String channelId, AuthData authData) {
        return chatRepository.deleteChannel(channelId);
    }
}
