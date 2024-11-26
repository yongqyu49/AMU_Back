package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

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
        Path file = Paths.get("C:/AMU/AMU Music").resolve(filename).normalize(); // 정확한 파일 경로 설정
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

    @GetMapping("/review/{musicCode}")
    public ResponseEntity<List<ReviewDTO>> reviewMusic(@PathVariable int musicCode) {
        System.out.println("음악 리뷰 musicCode: " + musicCode);
        List<ReviewDTO> reviewList = musicService.getReviewByMusicCode(musicCode);
        System.out.println("reviewList: " + reviewList);
        return ResponseEntity.ok(reviewList);
    }

    @PostMapping("/review/upload")
    public ResponseEntity<String> reviewMusic(@RequestBody ReviewDTO reviewDTO, HttpSession session) {
        System.out.println("음악 리뷰 작성 reviewDTO: " + reviewDTO);
        String id = (String) session.getAttribute("id");
        if(id == null) {
            System.out.println("id: " + id);
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        } else {
            reviewDTO.setId(id);
            int isUploaded = musicService.uploadReview(reviewDTO);
            if(isUploaded == 1) {
                return ResponseEntity.ok("리뷰 작성 성공");
            }else{
                return ResponseEntity.badRequest().body("리뷰 작성 실패");
            }
        }
    }

}
