package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;

import java.util.List;

public interface PlaylistService {
    String getDefaultPlaylist(String id);

    int addMusicToPlaylist(PlaylistDTO playlistDTO);

    List<MusicDTO> getDefaultPlaylistMusic(String id);
}
