package com.blogspot.sontx.bottle.server.model.service;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.BottleUser;
import com.blogspot.sontx.bottle.server.model.bean.LoginData;
import com.blogspot.sontx.bottle.server.model.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log4j
public class JwtTokenAuthService implements AuthService {
    @Value("${jwt.secret}")
    private String secret;
    private final UserRepository userRepository;

    @Autowired
    public JwtTokenAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private String createJwtToken(String uid) {
        return Jwts.builder()
                .setSubject(uid)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
