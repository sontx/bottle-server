package com.blogspot.sontx.bottle.server.model.service.auth;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.BottleUser;
import com.blogspot.sontx.bottle.server.model.bean.LoginData;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:bottle-config.properties")
public class FakeAuthService implements AuthService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${default.room.id}")
    private int defaultRoomId;

    private final PublicProfileRepository publicProfileRepository;

    @Autowired
    public FakeAuthService(PublicProfileRepository publicProfileRepository) {
        this.publicProfileRepository = publicProfileRepository;
    }

    @Override
    public BottleUser login(LoginData loginData) {
        if (loginData.getToken().equals("test")) {
            BottleUser bottleUser = new BottleUser();
            bottleUser.setToken(createJwtToken("i'm your boss"));
            bottleUser.setUid("i'm your boss");
            updatePublicProfileIfNecessary("i'm your boss");
            return bottleUser;
        }
        return null;
    }

    @Override
    public AuthData authenticateWithToken(String token) {
        if (token != null) {
            AuthData authData = new AuthData();
            authData.setUid("i'm your boss");
            return authData;
        }
        return null;
    }

    private void updatePublicProfileIfNecessary(String uid) {
        if (!publicProfileRepository.exists(uid)) {
            PublicProfileEntity publicProfile = new PublicProfileEntity();
            publicProfile.setAvatarUrl("https://scontent.fdad3-1.fna.fbcdn.net/v/t1.0-9/536012_153138571496960_213017345_n.jpg?oh=4c9ddadd76fbef40ea1f4cecf32e975c&oe=595BD2A1");
            publicProfile.setDisplayName("Trần Xuân Sơn");
            publicProfile.setId(uid);

            UserSettingEntity userSettingEntity = new UserSettingEntity();
            userSettingEntity.setPublicProfile(publicProfile);
            userSettingEntity.setCurrentRoomId(defaultRoomId);

            publicProfile.setUserSetting(userSettingEntity);

            publicProfileRepository.save(publicProfile);
        }
    }

    private String createJwtToken(String uid) {
        return Jwts.builder()
                .setSubject(uid)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
