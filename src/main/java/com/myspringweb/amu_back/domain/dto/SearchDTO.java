package com.myspringweb.amu_back.domain.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class SearchDTO {
    private String searchWord;
    private int searchCount;
    private Timestamp searchTime;
}

