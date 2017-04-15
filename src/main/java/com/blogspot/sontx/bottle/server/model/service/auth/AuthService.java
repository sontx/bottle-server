package com.blogspot.sontx.bottle.server.model.service.auth;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.BottleUser;
import com.blogspot.sontx.bottle.server.model.bean.LoginData;
import com.blogspot.sontx.bottle.server.model.bean.VerifyResult;

public interface AuthService {
    BottleUser login(LoginData loginData);

    AuthData authenticateWithToken(String token);

    VerifyResult verify(AuthData authData);
}
