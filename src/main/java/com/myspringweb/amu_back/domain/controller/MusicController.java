package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;
    private final Path audioDirectory = Paths.get("C:\\AMU Music");

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

    // 음악 목록 조회
    @PostMapping("/list")
    public ResponseEntity<List<MusicDTO>> getMusicList() {
        System.out.println("음악 목록 조회");
        List<MusicDTO> musicList = musicService.getAllMusic();
        System.out.println("musicList: " + musicList);
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/play/{filename}")
    public ResponseEntity<Resource> playMusic(@PathVariable String filename) throws Exception {
        System.out.println("음악 재생 filename: " + filename);
        filename = filename + ".mp3";
        Path file = Paths.get("C:/AMU Music").resolve(filename).normalize(); // 정확한 파일 경로 설정
        System.out.println("file: " + file);
        Resource resource = new UrlResource(file.toUri());
        System.out.println("resource: " + resource);

        if (!resource.exists()) {
            throw new RuntimeException("File not found: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg") // MIME 타입 설정
                .body(resource);
    }

}
