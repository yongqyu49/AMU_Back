package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/test")
    public String hello() {
        return "테스트입니다.";
    }

    @PostMapping("/api/getOneUser")
    public UserDTO getOneUser() {
        return userService.getUserById("q");
    }
}
