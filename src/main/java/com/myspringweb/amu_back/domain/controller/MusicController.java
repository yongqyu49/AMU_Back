package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMusic(@RequestBody MusicDTO musicDTO, HttpSession session) { //@Valid: 정규식
        System.out.println("음악 업로드");
        String id = (String) session.getAttribute("id");
        musicDTO.setId(id);


        boolean isUploaded = musicService.uploadMusic(musicDTO);
        if(isUploaded){
            return ResponseEntity.ok("업로드 성공");
        }else{
            return ResponseEntity.badRequest().body("업로드 실패");
        }

    }
}
