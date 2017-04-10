package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSettingEntity, Integer> {
    UserSettingEntity findOneByPublicProfileId(String userId);
}
