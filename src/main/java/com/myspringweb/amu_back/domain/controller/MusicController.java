package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {
//    private final MusicService musicService;

    @PostMapping("/upload")
    public String upload(@RequestBody MusicDTO musicDTO) {
        System.out.println("음악 업로드");
        return "음악 업로드";
    }
}
