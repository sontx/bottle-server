package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.GeoMessage;
import com.blogspot.sontx.bottle.server.model.bean.event.GeoMessageChanged;
import com.blogspot.sontx.bottle.server.model.service.message.GeoMessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/geo")
@Log4j
public class GeoRestController {
    private static final String WEBSOCKET_TOPIC = "/geo";

    private final GeoMessageService geoMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GeoRestController(GeoMessageService geoMessageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.geoMessageService = geoMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("messages")
    ResponseEntity getMessagesAroundLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("latitudeRadius") double latitudeRadius,
            @RequestParam("longitudeRadius") double longitudeRadius) {
        List<GeoMessage> geoMessages = geoMessageService.getMessagesAroundLocation(latitude, longitude, latitudeRadius, longitudeRadius);
        return geoMessages != null ? ResponseEntity.ok(geoMessages) : ResponseEntity.status(400).build();
    }

    @PostMapping("messages")
    ResponseEntity postMessage(@RequestBody GeoMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        log.info("posting " + message);
        GeoMessage geoMessage = geoMessageService.postMessage(message, (AuthData) authenticationToken.getPrincipal());

        if (geoMessage != null) {
            GeoMessageChanged geoMessageChanged = new GeoMessageChanged();
            geoMessageChanged.setId(geoMessage.getId());
            geoMessageChanged.setLatitude(geoMessage.getLatitude());
            geoMessageChanged.setLongitude(geoMessage.getLongitude());
            geoMessageChanged.setState("add");
            simpMessagingTemplate.convertAndSend(WEBSOCKET_TOPIC, geoMessageChanged);
        }

        return geoMessage != null ? ResponseEntity.ok(geoMessage) : ResponseEntity.status(400).build();
    }

    @PutMapping("messages/{messageId}")
    ResponseEntity editMessage(@PathVariable int messageId, @RequestBody GeoMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        log.info("updating " + message);
        GeoMessage geoMessage = geoMessageService.editMessage(messageId, message, (AuthData) authenticationToken.getPrincipal());

        if (geoMessage != null) {
            GeoMessageChanged geoMessageChanged = new GeoMessageChanged();
            geoMessageChanged.setId(geoMessage.getId());
            geoMessageChanged.setLatitude(geoMessage.getLatitude());
            geoMessageChanged.setLongitude(geoMessage.getLongitude());
            geoMessageChanged.setState("update");
            simpMessagingTemplate.convertAndSend(WEBSOCKET_TOPIC, geoMessageChanged);
        }

        return geoMessage != null ? ResponseEntity.ok(geoMessage) : ResponseEntity.status(400).build();
    }
}
