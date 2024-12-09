package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.FavoriteDTO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
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
    // private final Path audioDirectory = Paths.get("C:\\AMU Music");

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMusic(
            @RequestParam("title") String title,
            @RequestParam("lyrics") String lyrics,
            @RequestParam("filePath1") String filePath1,
            @RequestParam("filePath2") String filePath2,
            @RequestParam("fileSize") String fileSize,
            @RequestParam("fileMp3") MultipartFile fileMp3,
            @RequestParam("fileImg") MultipartFile fileImg,
            @RequestParam("playTime") String playTime,
            @RequestParam("genre") Integer genre,
            HttpSession session) {

        System.out.println("uploadMusic 시작");
        String id = (String) session.getAttribute("id");
        try {
            System.out.println("업로드 데이터 확인");
            System.out.println("title: " + title);
            System.out.println("lyrics: " + lyrics);
            System.out.println("filePath1: " + filePath1);
            System.out.println("filePath2: " + filePath2);
            System.out.println("fileSize: " + fileSize);
            System.out.println("playTime: " + playTime);
            System.out.println("genre: " + genre);
            System.out.println("id: " + id);
            
            MusicDTO musicDTO = new MusicDTO();
            musicDTO.setTitle(title);
            musicDTO.setLyrics(lyrics);
            musicDTO.setMp3Path(filePath1);
            musicDTO.setImgPath(filePath2);
            musicDTO.setFileSize(Long.parseLong(fileSize));
            musicDTO.setRuntime(Integer.parseInt(playTime));
            musicDTO.setGenreCode(genre);
            musicDTO.setReleaseDate(new Timestamp(System.currentTimeMillis()));
            musicDTO.setId(id);
            musicDTO.setViews(1);
            
            System.out.println("삽입 데이터");
            System.out.println(musicDTO);
            
            boolean isUploaded = musicService.uploadMusic(musicDTO);
            System.out.println("Service 호출");
            System.out.println("uploadMusic 결과: " + isUploaded);
            
            if(isUploaded) {
                return ResponseEntity.ok("업로드 성공");
            } else {
                return ResponseEntity.badRequest().body("업로드 실패");
            }
        } catch (Exception e) {
            System.out.println("=== 에러 발생 지점 확인 ===");
            System.out.println("에러 클래스: " + e.getClass().getName());
            System.out.println("에러 메시지: " + e.getMessage());
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }
    }

    // 음악 목록 조회
    @PostMapping("/list")
    public ResponseEntity<List<MusicDTO>> getMusicList() {
        List<MusicDTO> musicList = musicService.getAllMusic();
        return ResponseEntity.ok(musicList);
    }

    @PostMapping("/listLatest")
    public ResponseEntity<List<MusicDTO>> getMusicListLatest() {
        List<MusicDTO> musicList = musicService.getAllMusicLatest();
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/play/{filename}")
    public ResponseEntity<Resource> playMusic(@PathVariable String filename) throws Exception {
//        System.out.println("음악 재생 filename: " + filename);
        filename = filename + ".mp3";
        Path file = Paths.get("C:/AMU_asset/AMU Music").resolve(filename).normalize(); // 정확한 파일 경로 설정
//        System.out.println("file: " + file);
        Resource resource = new UrlResource(file.toUri());
//        System.out.println("resource: " + resource);

        if (!resource.exists()) {
            throw new RuntimeException("File not found: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg") // MIME 타입 설정
                .body(resource);
    }

    @GetMapping("/review/{musicCode}")
    public ResponseEntity<List<ReviewDTO>> reviewMusic(@PathVariable int musicCode) {
//        System.out.println("음악 리뷰 musicCode: " + musicCode);
        List<ReviewDTO> reviewList = musicService.getReviewByMusicCode(musicCode);
//        System.out.println("reviewList: " + reviewList);
        return ResponseEntity.ok(reviewList);
    }

    @PostMapping("/review/upload")
    public ResponseEntity<String> reviewMusic(@RequestBody ReviewDTO reviewDTO, HttpSession session) {
        System.out.println("음악 리뷰 작성 reviewDTO: " + reviewDTO);
        String id = (String) session.getAttribute("id");
        if(id == null) {
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

    @GetMapping("/getPlaylist")
    public ResponseEntity<List<MusicDTO>> getPlaylist(HttpSession session) {
        String id = (String)
                session.getAttribute("id");
        List<MusicDTO> musicList = musicService.getDefaultPlaylist(id);
        System.out.println("음악 리스트: " + musicList);
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/isLiked/{musicCode}")
    public ResponseEntity<Boolean> isLikedMusic(@PathVariable int musicCode, HttpSession session) {
        String id = (String) session.getAttribute("id");
        if(id == null)
            return ResponseEntity.ok(false);
        else {
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setId(id);
            favoriteDTO.setMusicCode(musicCode);
            int isLiked = musicService.isLikedMusic(favoriteDTO);
            if (isLiked == 1) return ResponseEntity.ok(true);
            else return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeMusic(@RequestBody FavoriteDTO favoriteDTO, HttpSession session) {
        String id = (String) session.getAttribute("id");
        if(id == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        } else {
            favoriteDTO.setId(id);
            int isLiked = musicService.likeMusic(favoriteDTO);
            if(isLiked == 1) {
                return ResponseEntity.ok("좋아요 성공");
            }else{
                return ResponseEntity.badRequest().body("좋아요 실패");
            }
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<String> unlikeMusic(@RequestBody FavoriteDTO favoriteDTO, HttpSession session) {
        String id = (String) session.getAttribute("id");
        if(id == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        } else {
            favoriteDTO.setId(id);
            int isLiked = musicService.unlikeMusic(favoriteDTO);
            if(isLiked == 1) {
                return ResponseEntity.ok("안 좋아요 성공");
            }else{
                return ResponseEntity.badRequest().body("안 좋아요 실패");
            }
        }
    }

}
