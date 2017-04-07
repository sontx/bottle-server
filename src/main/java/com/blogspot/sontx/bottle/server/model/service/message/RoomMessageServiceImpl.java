package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
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
            RoomMessageEntity roomMessageEntity = new RoomMessageEntity();
            roomMessageEntity.setText(message.getText());
            roomMessageEntity.setMediaUrl(message.getMediaUrl());
            roomMessageEntity.setPublicProfile(publicProfileEntity);
            roomMessageEntity.setRoom(roomEntity);

            RoomMessageEntity savedRoomMessageEntity = roomMessageRepository.saveAndFlush(roomMessageEntity);

            message.setId(savedRoomMessageEntity.getId());

            return message;
        }
        return null;
    }
}
