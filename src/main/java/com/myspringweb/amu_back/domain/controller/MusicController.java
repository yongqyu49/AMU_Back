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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicService musicService;
    // private final Path audioDirectory = Paths.get("C:\\AMU Music");
    @Value("${spring.web.resources.static-locations}")
    private String uploadDir;

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
            System.out.println("filePath1: " + filePath1); //mp3파일 이름
            System.out.println("filePath2: " + filePath2); //이미지 이름
            System.out.println("fileMp3: " + fileMp3); //mp3파일 원본
            System.out.println("fileImg: " + fileImg); //img파일 원본
            System.out.println("fileSize: " + fileSize);
            System.out.println("playTime: " + playTime);
            System.out.println("genre: " + genre);
            System.out.println("id: " + id);
            
            // String Mp3Path = "AMU Music/" + filePath1;
            // String ImgPath = "AMU_Img/" + filePath2;
            // System.out.println("음악 파일 경로: " + Mp3Path);
            // System.out.println("이미지 파일 경로: " + ImgPath);

            MusicDTO musicDTO = new MusicDTO();
            musicDTO.setTitle(title);
            musicDTO.setLyrics(lyrics);
            musicDTO.setFileSize(Long.parseLong(fileSize));
            musicDTO.setRuntime(Integer.parseInt(playTime));
            musicDTO.setGenreCode(genre);
            musicDTO.setReleaseDate(new Timestamp(System.currentTimeMillis()));
            musicDTO.setArtist(id);
            musicDTO.setViews(1);
            
            System.out.println("삽입 데이터");
            System.out.println(musicDTO);
            
            // 이미지 파일 저장
            String imgFileName = UUID.randomUUID().toString() + "_" + fileImg.getOriginalFilename();
            String imgPath = "C:/AMU_asset/AMU_Img/" + imgFileName;
            fileImg.transferTo(new File(imgPath));

            musicDTO.setImgPath("AMU_Img/" + imgFileName);

            // 음악 파일 저장
            String mp3FileName = fileMp3.getOriginalFilename();
            String mp3Path = "C:/AMU_asset/AMU Music/" + mp3FileName;
            fileMp3.transferTo(new File(mp3Path));

            System.out.println("imgPath: " + imgPath);
            System.out.println("mp3Path: " + mp3Path);

            // musicDTO.setImgPath("AMU_Img/" + imgFileName);
            musicDTO.setMp3Path("AMU Music/" + mp3FileName);

            // System.out.println("musicDTO: " + musicDTO);
            
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
            System.out.println("���러 메시지: " + e.getMessage());
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }        
    }

    //이미지 조회
    @GetMapping("/getMusic/image/{musicCode}")
    public ResponseEntity<Resource> showImage(@PathVariable int musicCode) throws Exception {
        String ImagePath = musicService.getImgPathByMusicCode(musicCode);

        System.out.println("musicCode: " + musicCode);
        System.out.println("원본 이미지 경로: " + ImagePath);

        String realPath = ImagePath.replace("file:///", "");


        Resource resource = new FileSystemResource(realPath);
        // 파일이 존재하는지 확인
        if (resource.exists() && resource.isReadable()) {
            System.out.println("파일 존재 확인 성공");

            return ResponseEntity.ok()
                // .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
        } else {
            System.out.println("파일을 찾을 수 없음: " + realPath);
            return ResponseEntity.notFound().build();
        }
    }

   @GetMapping("/sort/{sortType}")
   public ResponseEntity<List<MusicDTO>> getMusicSort(@PathVariable String sortType) {
       System.out.println("sortType: " + sortType);
       List<MusicDTO> musicList = musicService.getAllMusicSorted(sortType);
       return ResponseEntity.ok(musicList);
   }

   @GetMapping("/genre/{genreCode}")
   public List<MusicDTO> getShowGenre(@PathVariable int genreCode) {
       System.out.println("장르 선별 요청: " + genreCode);
       if(genreCode == 0) {
           return musicService.getAllMusic();
       } else {
           List<MusicDTO> result = musicService.getShowGenre(genreCode);
           System.out.println("조회된 음악 수: " + result.size());
           return result;
       }
   }

//   @PostMapping()

    //음악 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMusic(@RequestParam int musicCode) {
        System.out.println("deleteMusic 호출");
        int result = musicService.deleteMusic(musicCode);
        if(result == 1) {
            return ResponseEntity.ok("음악 삭제 성공");
        }else{
            return ResponseEntity.badRequest().body("음악 삭제 실패");
        }
    }
    
    //조회수 추가
    @PostMapping("/view")
    public ResponseEntity<String> addViews(@RequestParam int musicCode) {
        System.out.println("addViews 호출");
        int result = musicService.updateViews(musicCode);
        System.out.println("musicCode for Views: " + musicCode);
        if(result == 1) {
            return ResponseEntity.ok("조회수 추가 성공");
        }else{
            return ResponseEntity.badRequest().body("조회수 추가 실패");
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

    @GetMapping("/{musicCode}")
    public ResponseEntity<MusicDTO> musicDetail(@PathVariable String musicCode, HttpSession session) {
        String id = (String) session.getAttribute("id");
        MusicDTO musicDetail = musicService.getMusicById(Integer.parseInt(musicCode));
        System.out.println("musicDetail" + musicDetail);
        return ResponseEntity.ok(musicDetail);
    }

    @GetMapping("/{musicCode}/comments")
    public ResponseEntity<List<ReviewDTO>> musicDetailComment(@PathVariable String musicCode, HttpSession session) {
        List<ReviewDTO> reivewList = musicService.getMusicReviewList(Integer.parseInt(musicCode));
        System.out.println("reivewList" + reivewList);
        return ResponseEntity.ok(reivewList);
    }

    @GetMapping("/{musicCode}/commentCounts")
    public ResponseEntity<Integer> musicDetailCommentCounts(@PathVariable String musicCode, HttpSession session) {
        int reviewCounts = musicService.getMusicReviewCounts(Integer.parseInt(musicCode));
        return ResponseEntity.ok(reviewCounts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicDTO>> searchMusic(@RequestParam("query") String query) {
        System.out.println("검색어: " + query);
        List<MusicDTO> results = musicService.searchMusic(query);
        System.out.println("검색 결과: " + results);
        return ResponseEntity.ok(results);
    }

}
