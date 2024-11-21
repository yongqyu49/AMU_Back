package com.myspringweb.amu_back.domain.dao;

import com.myspringweb.amu_back.domain.dto.MusicDTO;

import java.util.List;

public interface MusicDAO {
    int uploadMusic(MusicDTO MusicDTO); //MusicDTO에서 MusicDTO클래스
    List<MusicDTO> getAllMusic();
    MusicDTO getMusicById(int id);
}
