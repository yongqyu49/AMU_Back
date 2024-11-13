package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("회원가입 요청");
        int result = userService.signUp(userDTO);
        if (result == 0) {
            return ResponseEntity.badRequest().body("회원가입 실패");
        } else {
            return ResponseEntity.ok("회원가입 성공");
        }
    }
}
