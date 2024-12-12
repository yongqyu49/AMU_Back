package com.myspringweb.amu_back.domain.controller;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;
import com.myspringweb.amu_back.domain.service.PlaylistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {
    private final Path audioDirectory = Paths.get("C:\\AMU Music");
    private final PlaylistService playlistService;

    @GetMapping("/getPlaylist")
    public ResponseEntity<List<MusicDTO>> getPlaylist(HttpSession session) {
        System.out.println("getPlaylist 호출");
        String id = (String)session.getAttribute("id");
        List<MusicDTO> musicList = playlistService.getDefaultPlaylistMusic(id);
        System.out.println("음악 리스트: " + musicList);
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/play/{filename}")
    public Resource getAudio(@PathVariable String filename) throws Exception {
        Path file = audioDirectory.resolve(filename);
        return new UrlResource(file.toUri());
    }

    @PostMapping("/addMusic")
    public ResponseEntity<String> addMusicToPlaylist(@RequestBody Map<String, String> requestData, HttpSession session) {
        String id = (String) session.getAttribute("id");
        String musicCode = requestData.get("musicCode");
        System.out.println("Received musicCode: " + musicCode);
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setMusicCode(Integer.parseInt(musicCode));

        // default_playlist 선택
        String playlistCode = playlistService.getDefaultPlaylist(id);
        playlistDTO.setPlaylistCode(Integer.parseInt(playlistCode));
        int result = playlistService.addMusicToPlaylist(playlistDTO);

        if (result == 1) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
