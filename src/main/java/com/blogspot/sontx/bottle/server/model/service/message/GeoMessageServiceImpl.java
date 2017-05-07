package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.GeoMessage;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;
import com.blogspot.sontx.bottle.server.model.entity.GeoMessageEntity;
import com.blogspot.sontx.bottle.server.model.entity.MessageDetailEntity;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import com.blogspot.sontx.bottle.server.model.repository.GeoMessageRepository;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import com.blogspot.sontx.bottle.server.model.repository.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:bottle-config.properties")
public class GeoMessageServiceImpl implements GeoMessageService {
    @Value("${default.geo.radius}")
    private double defaultRadius;
    private final GeoMessageRepository geoMessageRepository;
    private final PublicProfileRepository publicProfileRepository;
    private final UserSettingRepository userSettingRepository;

    @Autowired
    public GeoMessageServiceImpl(GeoMessageRepository geoMessageRepository, PublicProfileRepository publicProfileRepository, UserSettingRepository userSettingRepository) {
        this.geoMessageRepository = geoMessageRepository;
        this.publicProfileRepository = publicProfileRepository;
        this.userSettingRepository = userSettingRepository;
    }

    @Override
    @Transactional
    public List<GeoMessage> getMessagesAroundLocation(double latitude, double longitude, double latitudeRadius, double longitudeRadius) {
        double latitudeTop = latitude + latitudeRadius;
        double latitudeBottom = latitude - latitudeRadius;
        double longitudeLeft = longitude - longitudeRadius;
        double longitudeRight = longitude + longitudeRadius;
        List<GeoMessageEntity> allAroundLocation = geoMessageRepository.findAllAroundLocation(latitudeTop, latitudeBottom, longitudeLeft, longitudeRight);

        List<GeoMessage> geoMessages = new ArrayList<>(allAroundLocation.size());
        for (GeoMessageEntity geoMessageEntity : allAroundLocation) {
            GeoMessage geoMessage = getGeoMessage(geoMessageEntity);
            geoMessages.add(geoMessage);
        }

        return geoMessages;
    }

    @Override
    public GeoMessage postMessage(GeoMessage message, AuthData authData) {
        PublicProfileEntity publicProfileEntity = publicProfileRepository.findOne(authData.getUid());
        if (publicProfileEntity != null) {
            MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
            messageDetailEntity.setText(message.getText());
            messageDetailEntity.setType(message.getType());
            messageDetailEntity.setMediaUrl(message.getMediaUrl());

            GeoMessageEntity geoMessageEntity = new GeoMessageEntity();
            geoMessageEntity.setAddressName(message.getAddressName());
            geoMessageEntity.setLatitude(message.getLatitude());
            geoMessageEntity.setLongitude(message.getLongitude());
            geoMessageEntity.setMessageDetail(messageDetailEntity);
            geoMessageEntity.setPublicProfile(publicProfileEntity);

            geoMessageRepository.saveAndFlush(geoMessageEntity);

            message.setId(geoMessageEntity.getId());
            message.setTimestamp(messageDetailEntity.getTimestamp().getTime());

            updateUserSettingIfNecessary(publicProfileEntity, geoMessageEntity);

            return message;
        }
        return null;
    }

    @Transactional
    private void updateUserSettingIfNecessary(PublicProfileEntity currentUser, GeoMessageEntity geoMessageEntity) {
        UserSettingEntity userSettingEntity = currentUser.getUserSetting();
        if (userSettingEntity.getMessageEntity() == null) {
            userSettingEntity.setMessageEntity(geoMessageEntity);
            userSettingRepository.save(userSettingEntity);
        }
    }

    @Override
    @Transactional
    public GeoMessage editMessage(int messageId, GeoMessage message, AuthData authData) {

        GeoMessageEntity geoMessageEntity = geoMessageRepository.findOne(messageId);
        if (geoMessageEntity == null)
            return null;

        if (!geoMessageEntity.getPublicProfile().getId().equalsIgnoreCase(authData.getUid()))
            return null;

        geoMessageEntity.setAddressName(message.getAddressName());
        geoMessageEntity.setLatitude(message.getLatitude());
        geoMessageEntity.setLongitude(message.getLongitude());

        MessageDetailEntity messageDetailEntity = geoMessageEntity.getMessageDetail();
        messageDetailEntity.setText(message.getText());
        messageDetailEntity.setType(message.getType());
        messageDetailEntity.setMediaUrl(message.getMediaUrl());

        geoMessageRepository.saveAndFlush(geoMessageEntity);

        message.setId(geoMessageEntity.getId());
        message.setTimestamp(messageDetailEntity.getTimestamp().getTime());

        return message;

    }

    @Override
    @Transactional
    public GeoMessage getMessage(int messageId) {
        GeoMessageEntity messageEntity = geoMessageRepository.findOne(messageId);
        return messageEntity != null ? getGeoMessage(messageEntity) : null;
    }

    private GeoMessage getGeoMessage(GeoMessageEntity geoMessageEntity) {
        MessageDetailEntity messageDetail = geoMessageEntity.getMessageDetail();
        GeoMessage geoMessage = new GeoMessage();

        geoMessage.setId(geoMessageEntity.getId());

        geoMessage.setText(messageDetail.getText());
        geoMessage.setMediaUrl(messageDetail.getMediaUrl());
        geoMessage.setType(messageDetail.getType());
        geoMessage.setTimestamp(messageDetail.getTimestamp().getTime());

        geoMessage.setAddressName(geoMessageEntity.getAddressName());
        geoMessage.setLatitude(geoMessageEntity.getLatitude());
        geoMessage.setLongitude(geoMessageEntity.getLongitude());

        PublicProfileEntity publicProfileEntity = geoMessageEntity.getPublicProfile();
        PublicProfile publicProfile = new PublicProfile();
        publicProfile.setId(publicProfileEntity.getId());
        publicProfile.setDisplayName(publicProfileEntity.getDisplayName());
        publicProfile.setAvatarUrl(publicProfileEntity.getAvatarUrl());

        geoMessage.setOwner(publicProfile);
        return geoMessage;
    }
}
