package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
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

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody UserDTO userDTO, HttpSession session) {
        System.out.println("로그인 요청");
        int result = userService.signIn(userDTO);

        if (result == 0) {
            return ResponseEntity.badRequest().body("존재하지 않는 아이디입니다.");
        } else if (result == 1) {
            session.setAttribute("id", userDTO.getId());
            session.setAttribute("artist", userDTO.getArtist());
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
    }

}
