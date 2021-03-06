package com.blogspot.sontx.bottle.server.model.service.usersetting;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.Task;
import com.blogspot.sontx.bottle.server.model.bean.UserSetting;

public interface UserSettingService {
    Task<UserSetting> getUserSetting(String userId, AuthData authData);

    Task<UserSetting> updateUserSetting(String userId, UserSetting userSetting, AuthData authData);
}
