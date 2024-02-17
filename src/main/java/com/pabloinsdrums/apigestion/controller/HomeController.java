package com.pabloinsdrums.apigestion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
       return "sistema de gestion de productos test";
    }
}
