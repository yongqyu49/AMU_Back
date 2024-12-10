package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Value("${spring.file.upload-dir}")
    private String uploadDir;

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
        UserDTO user = userService.signIn(userDTO);

        if (user == null)
            return ResponseEntity.badRequest().body("존재하지 않는 아이디거나 비밀번호가 일치하지 않습니다.");

        session.setAttribute("id", user.getId());
        session.setAttribute("artist", user.getArtist());
        session.setAttribute("role", user.getRole());
        session.setAttribute("profile_img", user.getProfileImg());
        return ResponseEntity.ok("로그인 성공");
    }

    // 세션 확인
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(HttpSession session) {
        String id = (String) session.getAttribute("id");
        String artist = (String) session.getAttribute("artist");
        String role = (String) session.getAttribute("role");
        String profileImg = (String) session.getAttribute("profile_img");

        if (id != null && artist != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setArtist(artist);
            userDTO.setRole(role);
            userDTO.setProfileImg(profileImg);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/signOut")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> signOut(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(
            @RequestParam("artist") String artist,
            @RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
            HttpSession session) {
        String id = (String) session.getAttribute("id");

        if (id == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        try {
            // 기존 사용자 정보 가져오기
            UserDTO existingUser = userService.getUserById(id);
            String oldProfileImgPath = existingUser.getProfileImg();

            // 1. 파일 저장
            String filePath = null;
            String uniqueFileName = null;
            if (profileImg != null && !profileImg.isEmpty()) {
                uniqueFileName = UUID.randomUUID().toString() + "_" + profileImg.getOriginalFilename();
                filePath = uploadDir + uniqueFileName;
                System.out.println("파일 경로: " + filePath);
                File file = new File(filePath);
                profileImg.transferTo(file); // 파일 저장
            }

            // 2. 유저 정보 업데이트
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setArtist(artist);
            userDTO.setProfileImg(filePath != null ? "profile_img/" + uniqueFileName : oldProfileImgPath);

            int result = userService.updateProfile(userDTO);
            if (result == 1) {
                // 기존 이미지 삭제
                if (oldProfileImgPath != null && !oldProfileImgPath.equals(userDTO.getProfileImg())) {
                    File oldFile = new File(uploadDir + oldProfileImgPath.replace("profile_img/", ""));
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                session.setAttribute("artist", userDTO.getArtist());
                session.setAttribute("profile_img", userDTO.getProfileImg());
                return ResponseEntity.ok("프로필 수정 성공");
            } else {
                return ResponseEntity.badRequest().body("프로필 수정 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @GetMapping("/myUpload")
    public ResponseEntity<List<MusicDTO>> myUpload(HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) return ResponseEntity.badRequest().body(null);
        List<MusicDTO> musicList = userService.getMyUploadList(id);
        return ResponseEntity.ok(musicList);
    }

}
