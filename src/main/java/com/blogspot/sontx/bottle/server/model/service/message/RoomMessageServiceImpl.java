package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.entity.MessageDetailEntity;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.entity.RoomEntity;
import com.blogspot.sontx.bottle.server.model.entity.RoomMessageEntity;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import com.blogspot.sontx.bottle.server.model.repository.RoomMessageRepository;
import com.blogspot.sontx.bottle.server.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:bottle-config.properties")
public class RoomMessageServiceImpl implements RoomMessageService {
    @Value("${default.page.size}")
    private int defaultPageSize;
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
    public RoomMessage postMessage(int roomId, RoomMessage message, AuthData authData) {
        RoomEntity roomEntity = roomRepository.findOne(roomId);
        PublicProfileEntity publicProfileEntity = publicProfileRepository.findOne(authData.getUid());

        if (roomEntity != null && publicProfileEntity != null) {
            MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
            messageDetailEntity.setText(message.getText());
            messageDetailEntity.setType(message.getType());
            messageDetailEntity.setMediaUrl(message.getMediaUrl());

            RoomMessageEntity roomMessageEntity = new RoomMessageEntity();
            roomMessageEntity.setMessageDetail(messageDetailEntity);
            roomMessageEntity.setPublicProfile(publicProfileEntity);
            roomMessageEntity.setRoom(roomEntity);

            roomMessageRepository.saveAndFlush(roomMessageEntity);

            message.setId(roomMessageEntity.getId());
            message.setRoomId(roomId);
            message.setTimestamp(messageDetailEntity.getTimestamp().getTime());

            return message;
        }
        return null;
    }

    @Override
    @Transactional
    public List<RoomMessage> getMessages(int roomId, int page, int pageSize) {
        if (page < 0)
            page = 0;
        if (pageSize <= 0)
            pageSize = defaultPageSize;
        Pageable pageable = new PageRequest(page, pageSize);
        Page<RoomMessageEntity> roomEntities = roomMessageRepository.findAllByRoomIdOrderByMessageDetail_TimestampDesc(roomId, pageable);

        List<RoomMessage> roomMessages = new ArrayList<>(roomEntities.getSize());
        for (RoomMessageEntity roomEntity : roomEntities) {
            RoomMessage room = createRoomMessageFromEntity(roomEntity);
            roomMessages.add(room);
        }

        return roomMessages;
    }

    @Override
    @Transactional
    public RoomMessage updateMessage(int messageId, RoomMessage message, AuthData authData) {
        RoomMessageEntity roomMessageEntity = roomMessageRepository.findOne(messageId);

        if (roomMessageEntity == null)
            return null;

        if (!roomMessageEntity.getPublicProfile().getId().equals(authData.getUid()))
            return null;

        MessageDetailEntity messageDetailEntity = roomMessageEntity.getMessageDetail();
        messageDetailEntity.setText(message.getText());
        messageDetailEntity.setMediaUrl(message.getMediaUrl());
        messageDetailEntity.setTimestamp(new Timestamp(new Date().getTime()));

        roomMessageRepository.save(roomMessageEntity);

        message.setTimestamp(messageDetailEntity.getTimestamp().getTime());
        message.setRoomId(roomMessageEntity.getRoom().getId());

        return message;
    }

    @Override
    @Transactional
    public RoomMessage getMessage(int messageId) {
        RoomMessageEntity roomMessageEntity = roomMessageRepository.findOne(messageId);
        if (roomMessageEntity == null)
            return null;
        return createRoomMessageFromEntity(roomMessageEntity);
    }

    @Override
    @Transactional
    public RoomMessage deleteMessage(int messageId, AuthData authData) {
        RoomMessageEntity roomMessageEntity = roomMessageRepository.findOne(messageId);
        if (roomMessageEntity == null)
            return null;
        if (!roomMessageEntity.getPublicProfile().getId().equals(authData.getUid()))
            return null;
        RoomMessage roomMessage = createRoomMessageFromEntity(roomMessageEntity);
        roomMessageRepository.removeOneByIdEquals(messageId);
        return roomMessage;
    }

    private RoomMessage createRoomMessageFromEntity(RoomMessageEntity roomMessageEntity) {
        RoomMessage roomMessage = new RoomMessage();

        roomMessage.setId(roomMessageEntity.getId());
        roomMessage.setRoomId(roomMessageEntity.getRoom().getId());

        MessageDetailEntity messageDetail = roomMessageEntity.getMessageDetail();
        roomMessage.setText(messageDetail.getText());
        roomMessage.setMediaUrl(messageDetail.getMediaUrl());
        roomMessage.setType(messageDetail.getType());
        roomMessage.setTimestamp(messageDetail.getTimestamp().getTime());

        PublicProfileEntity publicProfileEntity = roomMessageEntity.getPublicProfile();
        PublicProfile publicProfile = new PublicProfile();
        publicProfile.setId(publicProfileEntity.getId());
        publicProfile.setDisplayName(publicProfileEntity.getDisplayName());
        publicProfile.setAvatarUrl(publicProfileEntity.getAvatarUrl());

        roomMessage.setOwner(publicProfile);

        return roomMessage;
    }
}
