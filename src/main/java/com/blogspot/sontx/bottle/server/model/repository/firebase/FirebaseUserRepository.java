package com.blogspot.sontx.bottle.server.model.repository.firebase;

import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class FirebaseUserRepository implements UserRepository {
    private final FirebaseManager firebaseManager;

    @Autowired
    public FirebaseUserRepository(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    @Override
    public String findIdByToken(String token) {
        try {
            return firebaseManager.getUserIdByToken(token);
        } catch (InterruptedException e) {
            log.error(e);
        }
        return null;
    }

    @Override
    public PublicProfileEntity findUserById(String uid) {
        try {
            return firebaseManager.getPublicProfileById(uid);
        } catch (InterruptedException e) {
            log.error(e);
        }
        return null;
    }
}
