package com.blogspot.sontx.bottle.server.model.service.auth;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.BottleUser;
import com.blogspot.sontx.bottle.server.model.bean.LoginData;
import com.blogspot.sontx.bottle.server.model.bean.VerifyResult;
import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.blogspot.sontx.bottle.server.model.entity.UserSettingEntity;
import com.blogspot.sontx.bottle.server.model.repository.PublicProfileRepository;
import com.blogspot.sontx.bottle.server.model.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Log4j
@Service
@PropertySource("classpath:bottle-config.properties")
public class JwtTokenAuthService implements AuthService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${default.room.id}")
    private int defaultRoomId;

    private final UserRepository userRepository;
    private final PublicProfileRepository publicProfileRepository;

    @Autowired
    public JwtTokenAuthService(UserRepository userRepository, PublicProfileRepository publicProfileRepository) {
        this.userRepository = userRepository;
        this.publicProfileRepository = publicProfileRepository;
    }

    @Override
    public BottleUser login(LoginData loginData) {
        if (loginData != null && !StringUtils.isEmpty(loginData.getToken())) {
            String uid = userRepository.findIdByToken(loginData.getToken());
            if (!StringUtils.isEmpty(uid)) {
                String jwtToken = createJwtToken(uid);

                BottleUser bottleUser = new BottleUser();
                bottleUser.setToken(jwtToken);
                bottleUser.setUid(uid);

                log.info("login success: uuid = " + uid);

                updatePublicProfileIfNecessary(uid);

                return bottleUser;
            }
        }
        log.error("invalid login data.");
        return null;
    }

    @Override
    public AuthData authenticateWithToken(String token) {
        if (!StringUtils.isEmpty(token)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                String uid = claimsJws.getBody().getSubject();

                AuthData authData = new AuthData();
                authData.setUid(uid);

                return authData;
            } catch (SignatureException ex) {
                log.error(ex);
            }
        }
        return null;
    }

    @Override
    public VerifyResult verify(AuthData authData) {
        if (authData == null)
            return null;
        VerifyResult verifyResult = new VerifyResult();
        verifyResult.setUserId(authData.getUid());
        return verifyResult;
    }

    private void updatePublicProfileIfNecessary(String uid) {
        if (!publicProfileRepository.exists(uid)) {
            PublicProfileEntity publicProfileEntity = userRepository.findUserById(uid);

            UserSettingEntity userSettingEntity = new UserSettingEntity();
            userSettingEntity.setPublicProfile(publicProfileEntity);
            userSettingEntity.setCurrentRoomId(defaultRoomId);

            publicProfileEntity.setUserSetting(userSettingEntity);

            publicProfileRepository.save(publicProfileEntity);
        }
    }

    private String createJwtToken(String uid) {
        return Jwts.builder()
                .setSubject(uid)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
