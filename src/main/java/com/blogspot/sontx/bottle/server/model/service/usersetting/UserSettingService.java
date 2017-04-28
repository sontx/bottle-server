package com.blogspot.sontx.bottle.server.model.service.usersetting;

import com.blogspot.sontx.bottle.server.model.bean.UserSetting;

public interface UserSettingService {
    UserSetting getUserSetting(String userId);

    UserSetting updateUserSetting(String userId, UserSetting userSetting);
}
