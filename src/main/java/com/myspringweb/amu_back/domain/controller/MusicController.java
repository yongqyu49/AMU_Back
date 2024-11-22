package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;

    @PostMapping("/upload")

    public ResponseEntity<?> uploadMusic(
            @RequestPart("fileMp3") MultipartFile fileMp3,
            @RequestPart("fileImg") MultipartFile fileImg,
            @RequestPart("fileSize") String fileSize,
            @RequestPart("filePath1") String filePath1,
            @RequestPart("filePath2") String filePath2,
            @RequestPart("playTime") String playTime,
            @RequestPart("title") String title,
            @RequestPart("genre") String genre,
            @RequestPart("lyrics") String lyrics,
            HttpSession session) { //@Valid: 정규식
        System.out.println("음악 업로드");

        MusicDTO musicDTO = new MusicDTO();

        String id = (String) session.getAttribute("id");
        musicDTO.setId(id);

        long realFileSize = Long.parseLong(fileSize);
        int runTIme = Integer.parseInt(playTime);

        musicDTO.setMusicCode(1); //int
        musicDTO.setTitle(title);
        musicDTO.setLyrics(lyrics);
        musicDTO.setReleaseDate(new Timestamp(System.currentTimeMillis()));
        musicDTO.setMp3Path(filePath1);
        musicDTO.setImgPath(filePath2);
        musicDTO.setFileSize(realFileSize);
        musicDTO.setRuntime(runTIme);
        musicDTO.setViews(1);
        musicDTO.setGenreCode(1); //int
        musicDTO.setId(id);  // 현재 세션의 사용자 ID 설정



        boolean isUploaded = musicService.uploadMusic(musicDTO);
        if(isUploaded){
            System.out.println("업로드 성공");
            return ResponseEntity.ok("업로드 성공");
        }else{
            System.out.println("업로드 실패");
            return ResponseEntity.badRequest().body("업로드 실패");
        }
    }
}
