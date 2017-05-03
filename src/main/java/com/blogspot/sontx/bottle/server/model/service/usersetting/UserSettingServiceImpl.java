package com.blogspot.sontx.bottle.server.model.service.usersetting;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.Coordination;
import com.blogspot.sontx.bottle.server.model.bean.UserSetting;
import com.blogspot.sontx.bottle.server.model.entity.GeoMessageEntity;
import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import com.blogspot.sontx.bottle.server.model.repository.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingRepository userSettingRepository;

    @Autowired
    public UserSettingServiceImpl(UserSettingRepository userSettingRepository) {
        this.userSettingRepository = userSettingRepository;
    }

    @Override
    @Transactional
    public UserSetting getUserSetting(String userId, AuthData authData) {
        if (StringUtils.isEmpty(userId))
            return null;

        if (!userId.equals(authData.getUid()))
            return null;

        UserSettingEntity userSettingEntity = userSettingRepository.findOneByPublicProfileId(userId);

        if (userSettingEntity == null) {
            return null;
        }

        UserSetting userSetting = new UserSetting();
        userSetting.setCurrentRoomId(userSettingEntity.getCurrentRoomId());

        Coordination coordination = new Coordination();
        coordination.setLatitude(userSettingEntity.getCurrentLatitude());
        coordination.setLongitude(userSettingEntity.getCurrentLongitude());
        userSetting.setCurrentLocation(coordination);

        GeoMessageEntity messageEntity = userSettingEntity.getMessageEntity();
        userSetting.setMessageId(messageEntity != null ? messageEntity.getId() : -1);

        return userSetting;
    }

    @Override
    public UserSetting updateUserSetting(String userId, UserSetting userSetting, AuthData authData) {
        if (StringUtils.isEmpty(userId))
            return null;

        if (!userId.equals(authData.getUid()))
            return null;

        UserSettingEntity userSettingEntity = userSettingRepository.findOneByPublicProfileId(userId);
        if (userSettingEntity != null) {

            userSettingEntity.setCurrentRoomId(userSetting.getCurrentRoomId());

            Coordination currentLocation = userSetting.getCurrentLocation();
            if (currentLocation != null) {
                userSettingEntity.setCurrentLatitude(currentLocation.getLatitude());
                userSettingEntity.setCurrentLongitude(currentLocation.getLongitude());
            }

            userSettingRepository.save(userSettingEntity);

            return userSetting;
        }

        return null;
    }
}
