package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.Room;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.service.message.RoomMessageService;
import com.blogspot.sontx.bottle.server.model.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
public class RoomRestController {
    private final RoomMessageService roomMessageService;
    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomMessageService roomMessageService, RoomService roomService) {
        this.roomMessageService = roomMessageService;
        this.roomService = roomService;
    }

    @GetMapping("{roomId}")
    ResponseEntity getRoom(@PathVariable int roomId) {
        Room room = roomService.getRoom(roomId);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.status(400).build();
    }

    @PostMapping("{roomId}/messages")
    ResponseEntity postMessage(@PathVariable int roomId, @RequestBody RoomMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        RoomMessage roomMessage = roomMessageService.postMessage(roomId, message, (AuthData) authenticationToken.getPrincipal());
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }

    @GetMapping("{roomId}/messages")
    ResponseEntity getMessages(@PathVariable int roomId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<RoomMessage> roomMessage = roomMessageService.getMessages(roomId, page, pageSize);
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }
}
