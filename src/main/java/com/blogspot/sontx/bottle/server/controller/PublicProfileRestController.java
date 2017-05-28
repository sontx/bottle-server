package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.PublicProfile;
import com.blogspot.sontx.bottle.server.model.service.profile.PublicProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/profile")
class PublicProfileRestController {
    private final PublicProfileService publicProfileService;

    @Autowired
    public PublicProfileRestController(PublicProfileService publicProfileService) {
        this.publicProfileService = publicProfileService;
    }

    @PutMapping("{userId}")
    ResponseEntity updateProfile(@PathVariable String userId, @RequestBody PublicProfile profile, UsernamePasswordAuthenticationToken authenticationToken) {
        profile = publicProfileService.updateProfile(userId, profile, (AuthData) authenticationToken.getPrincipal());
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.status(400).build();
    }
}
