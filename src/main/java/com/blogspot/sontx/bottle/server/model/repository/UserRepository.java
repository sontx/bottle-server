package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;

public interface UserRepository {
    String findIdByToken(String token);

    PublicProfileEntity findUserById(String uid);
}
