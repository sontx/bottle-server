package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.service.message.RoomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/messages/room")
public class RoomMessageRestController {
    private final RoomMessageService roomMessageService;

    @Autowired
    public RoomMessageRestController(RoomMessageService roomMessageService) {
        this.roomMessageService = roomMessageService;
    }

    @PostMapping
    ResponseEntity postMessage(@RequestBody RoomMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        RoomMessage roomMessage = roomMessageService.postMessage(message, (AuthData) authenticationToken.getPrincipal());
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }
}
