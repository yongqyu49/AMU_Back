package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.PlaylistDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaylistDAOImpl implements PlaylistDAO {
    private final SqlSession sqlSession;

    @Override
    public String getDefaultPlaylist(String id) {
        return sqlSession.selectOne("playlist.getDefaultPlaylist", id);
    }

    @Override
    public List<MusicDTO> getDefaultPlaylistMusic(String id) {
        return sqlSession.selectList("playlist.getDefaultPlaylistMusic", id);
    }

    @Override
    public int addMusicToPlaylist(PlaylistDTO playlistDTO) {
        return sqlSession.insert("playlist.addMusicToPlaylist", playlistDTO);
    }
}
