package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.GeoMessage;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;
import com.blogspot.sontx.bottle.server.model.entity.GeoMessageEntity;
import com.blogspot.sontx.bottle.server.model.entity.MessageDetailEntity;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.repository.GeoMessageRepository;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
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

    @Autowired
    public GeoMessageServiceImpl(GeoMessageRepository geoMessageRepository, PublicProfileRepository publicProfileRepository) {
        this.geoMessageRepository = geoMessageRepository;
        this.publicProfileRepository = publicProfileRepository;
    }

    @Override
    @Transactional
    public List<GeoMessage> getMessagesAroundLocation(double latitude, double longitude) {
        double latitudeTop = latitude + defaultRadius;
        double latitudeBottom = latitude - defaultRadius;
        double longitudeLeft = longitude - defaultRadius;
        double longitudeRight = longitude + defaultRadius;
        List<GeoMessageEntity> allAroundLocation = geoMessageRepository.findAllAroundLocation(latitudeTop, latitudeBottom, longitudeLeft, longitudeRight);

        List<GeoMessage> geoMessages = new ArrayList<>(allAroundLocation.size());
        for (GeoMessageEntity geoMessageEntity : allAroundLocation) {
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

            return message;
        }
        return null;
    }
}
