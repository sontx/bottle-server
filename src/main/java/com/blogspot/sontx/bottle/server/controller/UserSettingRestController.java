package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.UserSetting;
import com.blogspot.sontx.bottle.server.model.service.usersetting.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/settings")
class UserSettingRestController {
    private final UserSettingService userSettingService;

    @Autowired
    public UserSettingRestController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    @GetMapping("{userId}")
    ResponseEntity getUserSetting(@PathVariable String userId, UsernamePasswordAuthenticationToken authenticationToken) {
        UserSetting userSetting = userSettingService.getUserSetting(userId, (AuthData) authenticationToken.getPrincipal());
        return userSetting != null ? ResponseEntity.ok(userSetting) : ResponseEntity.status(400).build();
    }

    @PutMapping("{userId}")
    ResponseEntity updateUserSetting(@PathVariable String userId, @RequestBody UserSetting userSetting, UsernamePasswordAuthenticationToken authenticationToken) {
        userSetting = userSettingService.updateUserSetting(userId, userSetting, (AuthData) authenticationToken.getPrincipal());
        return userSetting != null ? ResponseEntity.ok(userSetting) : ResponseEntity.status(400).build();
    }
}
