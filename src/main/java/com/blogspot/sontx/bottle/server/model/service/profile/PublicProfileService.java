package com.blogspot.sontx.bottle.server.model.service.profile;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;

public interface PublicProfileService {
    PublicProfile updateProfile(String userId, PublicProfile profile, AuthData authData);
}
