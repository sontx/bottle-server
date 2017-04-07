package com.blogspot.sontx.bottle.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test connection and general information.
 */
@RestController
@RequestMapping("")
class HomeRestController {

    @GetMapping
    String home() {
        return "Float Bottle!";
    }
}
