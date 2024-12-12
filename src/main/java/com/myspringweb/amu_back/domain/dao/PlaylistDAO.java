package com.myspringweb.amu_back.domain.dao;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;

import java.util.List;

public interface PlaylistDAO {
    int addMusicToPlaylist(PlaylistDTO playlistDTO);

    String getDefaultPlaylist(String id);

    List<MusicDTO> getDefaultPlaylistMusic(String id);
}
