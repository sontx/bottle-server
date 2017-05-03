package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.GeoMessage;
import com.blogspot.sontx.bottle.server.model.service.message.GeoMessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/geo")
@Log4j
public class GeoRestController {
    private final GeoMessageService geoMessageService;

    @Autowired
    public GeoRestController(GeoMessageService geoMessageService) {
        this.geoMessageService = geoMessageService;
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
        return geoMessage != null ? ResponseEntity.ok(geoMessage) : ResponseEntity.status(400).build();
    }

    @PutMapping("messages/{messageId}")
    ResponseEntity editMessage(@PathVariable int messageId, @RequestBody GeoMessage message, UsernamePasswordAuthenticationToken authenticationToken) {
        log.info("updating " + message);
        GeoMessage geoMessage = geoMessageService.editMessage(messageId, message, (AuthData) authenticationToken.getPrincipal());
        return geoMessage != null ? ResponseEntity.ok(geoMessage) : ResponseEntity.status(400).build();
    }
}