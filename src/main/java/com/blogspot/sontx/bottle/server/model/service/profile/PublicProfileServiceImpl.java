package com.blogspot.sontx.bottle.server.model.service.profile;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PublicProfileServiceImpl implements PublicProfileService {
    private final PublicProfileRepository publicProfileRepository;

    @Autowired
    public PublicProfileServiceImpl(PublicProfileRepository publicProfileRepository) {
        this.publicProfileRepository = publicProfileRepository;
    }

    @Override
    public PublicProfile updateProfile(String userId, PublicProfile profile, AuthData authData) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }

        if (StringUtils.isEmpty(profile.getDisplayName()))
            return null;

        if (StringUtils.isEmpty(profile.getAvatarUrl()))
            return null;

        if (!userId.equals(authData.getUid())) {
            return null;
        }

        PublicProfileEntity publicProfileEntity = publicProfileRepository.findOne(userId);
        if (publicProfileEntity == null)
            return null;

        publicProfileEntity.setDisplayName(profile.getDisplayName());
        publicProfileEntity.setAvatarUrl(profile.getAvatarUrl());

        publicProfileRepository.save(publicProfileEntity);

        return profile;
    }
}
