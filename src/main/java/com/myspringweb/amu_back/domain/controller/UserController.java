package com.myspringweb.amu_back.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/test")
    public String hello() {
        return "테스트입니다.";
    }
}
