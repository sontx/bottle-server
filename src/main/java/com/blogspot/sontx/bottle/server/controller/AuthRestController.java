package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.BottleUser;
import com.blogspot.sontx.bottle.server.model.bean.LoginData;
import com.blogspot.sontx.bottle.server.model.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
class AuthRestController {
    private final AuthService authService;

    @Autowired
    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("auth")
    ResponseEntity auth(@RequestBody LoginData loginData) {
        BottleUser bottleUser = authService.login(loginData);
        return bottleUser != null ? ResponseEntity.ok(bottleUser) : ResponseEntity.status(401).build();
    }

}
