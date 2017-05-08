package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.Room;
import com.blogspot.sontx.bottle.server.model.bean.RoomMessage;
import com.blogspot.sontx.bottle.server.model.bean.event.RoomMessageChanged;
import com.blogspot.sontx.bottle.server.model.service.message.RoomMessageService;
import com.blogspot.sontx.bottle.server.model.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
public class RoomRestController {
    private static final String WEBSOCKET_TOPIC = "/room/";

    private final RoomMessageService roomMessageService;
    private final RoomService roomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RoomRestController(RoomMessageService roomMessageService, RoomService roomService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomMessageService = roomMessageService;
        this.roomService = roomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("{roomId}")
    ResponseEntity getRoom(@PathVariable int roomId) {
        Room room = roomService.getRoom(roomId);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.status(400).build();
    }

    @PostMapping("{roomId}/messages")
    ResponseEntity postMessage(@PathVariable int roomId, @RequestBody RoomMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        RoomMessage roomMessage = roomMessageService.postMessage(roomId, message, (AuthData) authenticationToken.getPrincipal());

        if (roomMessage != null) {
            String broadcast = WEBSOCKET_TOPIC + roomId;
            RoomMessageChanged roomMessageChanged = new RoomMessageChanged();
            roomMessageChanged.setId(roomMessage.getId());
            roomMessageChanged.setState("add");
            simpMessagingTemplate.convertAndSend(broadcast, roomMessageChanged);
        }

        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }

    @GetMapping("{roomId}/messages")
    ResponseEntity getMessages(@PathVariable int roomId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<RoomMessage> roomMessage = roomMessageService.getMessages(roomId, page, pageSize);
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }

    @GetMapping("messages/{messageId}")
    ResponseEntity getMessage(@PathVariable int messageId) {
        RoomMessage roomMessage = roomMessageService.getMessage(messageId);
        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }

    @PutMapping("messages/{messageId}")
    ResponseEntity updateMessage(@PathVariable int messageId, @RequestBody RoomMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        RoomMessage roomMessage = roomMessageService.updateMessage(messageId, message, (AuthData) authenticationToken.getPrincipal());

        if (roomMessage != null) {
            String broadcast = WEBSOCKET_TOPIC + roomMessage.getRoomId();
            RoomMessageChanged roomMessageChanged = new RoomMessageChanged();
            roomMessageChanged.setId(roomMessage.getId());
            roomMessageChanged.setState("update");
            simpMessagingTemplate.convertAndSend(broadcast, roomMessageChanged);
        }

        return roomMessage != null ? ResponseEntity.ok(roomMessage) : ResponseEntity.status(400).build();
    }
}
