package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.chat.CreateChannelRequest;
import com.blogspot.sontx.bottle.server.model.bean.chat.CreateChannelResult;
import com.blogspot.sontx.bottle.server.model.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/chat")
public class ChatRestController {
    private final ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/channels")
    ResponseEntity createChannel(@RequestBody CreateChannelRequest createChannelRequest, UsernamePasswordAuthenticationToken authenticationToken) {
        CreateChannelResult createChannelResult = chatService.createChannel(createChannelRequest.getBuddyId(), (AuthData) authenticationToken.getPrincipal());
        return createChannelResult != null ? ResponseEntity.ok(createChannelResult) : ResponseEntity.status(401).build();
    }
}
