package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.UserSetting;
import com.blogspot.sontx.bottle.server.model.service.usersetting.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity getUserSetting(@PathVariable String userId) {
        UserSetting userSetting = userSettingService.getUserSetting(userId);
        return userSetting != null ? ResponseEntity.ok(userSetting) : ResponseEntity.status(400).build();
    }

    @PutMapping("{userId")
    ResponseEntity updateUserSetting(@PathVariable String userId, @RequestBody UserSetting userSetting) {
        userSetting = userSettingService.updateUserSetting(userId, userSetting);
        return userSetting != null ? ResponseEntity.ok(userSetting) : ResponseEntity.status(400).build();
    }
}
