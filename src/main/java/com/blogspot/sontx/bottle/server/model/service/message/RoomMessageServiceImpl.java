package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.entity.MessageDetailEntity;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.entity.RoomEntity;
import com.blogspot.sontx.bottle.server.model.entity.RoomMessageEntity;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import com.blogspot.sontx.bottle.server.model.repository.RoomMessageRepository;
import com.blogspot.sontx.bottle.server.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomMessageServiceImpl implements RoomMessageService {
    private final RoomRepository roomRepository;
    private final RoomMessageRepository roomMessageRepository;
    private final PublicProfileRepository publicProfileRepository;

    @Autowired
    public RoomMessageServiceImpl(RoomRepository roomRepository, RoomMessageRepository roomMessageRepository, PublicProfileRepository publicProfileRepository) {
        this.roomRepository = roomRepository;
        this.roomMessageRepository = roomMessageRepository;
        this.publicProfileRepository = publicProfileRepository;
    }

    @Override
    public RoomMessage postMessage(RoomMessage message, AuthData authData) {
        RoomEntity roomEntity = roomRepository.findOne(message.getRoomId());
        PublicProfileEntity publicProfileEntity = publicProfileRepository.findOne(authData.getUid());

        if (roomEntity != null && publicProfileEntity != null) {
            MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
            messageDetailEntity.setText(message.getText());
            messageDetailEntity.setMediaUrl(message.getMediaUrl());

            RoomMessageEntity roomMessageEntity = new RoomMessageEntity();
            roomMessageEntity.setMessageDetail(messageDetailEntity);
            roomMessageEntity.setPublicProfile(publicProfileEntity);
            roomMessageEntity.setRoom(roomEntity);

            roomMessageRepository.saveAndFlush(roomMessageEntity);

            message.setId(roomMessageEntity.getId());
            message.setTimestamp(messageDetailEntity.getTimestamp().getTime());

            return message;
        }
        return null;
    }
}
