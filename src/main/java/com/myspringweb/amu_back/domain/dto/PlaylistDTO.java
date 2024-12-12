package com.myspringweb.amu_back.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PlaylistDTO {
    private int playlistCode;
    private String playlistName;
    private String id;
    private int musicCode;
    private Timestamp regTime;
    private Timestamp playlistDate;
}

