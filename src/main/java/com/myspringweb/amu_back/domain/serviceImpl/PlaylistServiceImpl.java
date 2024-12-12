package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.PlaylistDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;
import com.myspringweb.amu_back.domain.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistDAO playlistDAO;

    @Override
    public String getDefaultPlaylist(String id) {
        return playlistDAO.getDefaultPlaylist(id);
    }

    @Override
    public int addMusicToPlaylist(PlaylistDTO playlistDTO) {
        return playlistDAO.addMusicToPlaylist(playlistDTO);
    }

    @Override
    public List<MusicDTO> getDefaultPlaylistMusic(String id) {
        return playlistDAO.getDefaultPlaylistMusic(id);
    }
}
