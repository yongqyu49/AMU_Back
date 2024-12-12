package com.myspringweb.amu_back.domain.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class MusicDTO {
    private int musicCode;
    private String title;
    private String lyrics;
    private Timestamp releaseDate;
    private String mp3Path;
    private String imgPath;
    private long fileSize;
    private int runtime;
    private int views;
    private int genreCode;
    private String profileImg;
    private String id;

    private String artist;
}
