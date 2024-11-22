package com.myspringweb.amu_back.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
    private int reviewId;
    private String reviewContents;
    private int reviewLevel;
    private Timestamp reviewRegDate;
    private int reviewLike;

    private String id;
    private int musicId;
}
