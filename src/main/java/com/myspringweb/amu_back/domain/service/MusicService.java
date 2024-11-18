package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.MusicDTO;

public interface MusicService {
    boolean uploadMusic(MusicDTO musicDTO);
}
