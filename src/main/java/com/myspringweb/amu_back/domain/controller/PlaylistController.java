package com.myspringweb.amu_back.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final Path audioDirectory = Paths.get("C:\\AMU Music");

    @GetMapping("/play/{filename}")
    public Resource getAudio(@PathVariable String filename) throws Exception {
        Path file = audioDirectory.resolve(filename);
        return new UrlResource(file.toUri());
    }
}
