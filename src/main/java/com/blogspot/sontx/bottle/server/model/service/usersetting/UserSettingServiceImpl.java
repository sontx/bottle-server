package com.blogspot.sontx.bottle.server.model.service.usersetting;

import com.blogspot.sontx.bottle.server.model.bean.UserSetting;
import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import com.blogspot.sontx.bottle.server.model.repository.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingRepository userSettingRepository;

    @Autowired
    public UserSettingServiceImpl(UserSettingRepository userSettingRepository) {
        this.userSettingRepository = userSettingRepository;
    }

    @Override
    public UserSetting getUserSetting(String userId) {
        if (StringUtils.isEmpty(userId))
            return null;

        UserSettingEntity userSettingEntity = userSettingRepository.findOneByPublicProfileId(userId);

        UserSetting userSetting = new UserSetting();
        userSetting.setCurrentRoomId(userSettingEntity.getCurrentRoomId());

        return userSetting;
    }
}
