package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.MusicDTO;

import java.util.List;

public interface MusicService {
    boolean uploadMusic(MusicDTO musicDTO);
    List<MusicDTO> getAllMusic();
    MusicDTO getMusicById(int id);
}
