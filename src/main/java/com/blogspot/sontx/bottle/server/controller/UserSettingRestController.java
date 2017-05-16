package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.Task;
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
        Task<UserSetting> userSetting = userSettingService.getUserSetting(userId, (AuthData) authenticationToken.getPrincipal());
        return userSetting.getData() != null ? ResponseEntity.ok(userSetting.getData()) : ResponseEntity.status(400).body(userSetting.getMessage());
    }

    @PutMapping("{userId}")
    ResponseEntity updateUserSetting(@PathVariable String userId, @RequestBody UserSetting _userSetting, UsernamePasswordAuthenticationToken authenticationToken) {
        Task<UserSetting> userSetting = userSettingService.updateUserSetting(userId, _userSetting, (AuthData) authenticationToken.getPrincipal());
        return userSetting.getData() != null ? ResponseEntity.ok(userSetting.getData()) : ResponseEntity.status(400).body(userSetting.getMessage());
    }
}
