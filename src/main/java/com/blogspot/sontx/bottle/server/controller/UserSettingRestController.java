package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.UserSetting;
import com.blogspot.sontx.bottle.server.model.service.usersetting.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
