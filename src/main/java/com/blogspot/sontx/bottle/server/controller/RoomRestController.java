package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.service.message.RoomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/rooms")
public class RoomRestController {
    private final RoomMessageService roomMessageService;

    @Autowired
    public RoomRestController(RoomMessageService roomMessageService) {
        this.roomMessageService = roomMessageService;
    }

    @PostMapping("{roomId}/messages")
    ResponseEntity postMessage(@PathVariable int roomId, @RequestBody RoomMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        message.setRoomId(roomId);
        RoomMessage roomMessage = roomMessageService.postMessage(message, (AuthData) authenticationToken.getPrincipal());
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }
}
