package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.utils.SimpleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test connection and general information.
 */
@RestController
public class HomeRestController {

    @GetMapping("/test")
    public ResponseEntity testConnection() {
        return new ResponseEntity<>(new SimpleMessage("bottle", "Welcome to Bottle!"), HttpStatus.OK);
    }
}
