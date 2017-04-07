package com.blogspot.sontx.bottle.server.model.repository;

public interface UserRepository {
    String findIdByToken(String token);
}
