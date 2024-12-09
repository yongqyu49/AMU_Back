package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.MusicDAO;
import com.myspringweb.amu_back.domain.dto.FavoriteDTO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MusicDAOImpl implements MusicDAO {
    private final SqlSession sqlSession;

    @Override
    public int uploadMusic(MusicDTO musicDTO) {
        return sqlSession.insert("music.uploadMusic", musicDTO);
    }

    @Override
    public List<MusicDTO> getAllMusic() {
        return sqlSession.selectList("music.getAllMusic");
    }

    @Override
    public MusicDTO getMusicById(int id) {
        return sqlSession.selectOne("music.getMusicById", id);
    }

    @Override
    public List<ReviewDTO> getReviewByMusicCode(int musicCode) {
        return sqlSession.selectList("music.getReviewByMusicCode", musicCode);
    }

    @Override
    public int uploadReview(ReviewDTO reviewDTO) {
        int result = sqlSession.insert("music.uploadReview", reviewDTO);
        System.out.println("업로드??  " + result);
        return result;
    }

    @Override
    public List<MusicDTO> getAllMusicLatest() {
        return sqlSession.selectList("music.getAllMusicLatest");
    }

    @Override
    public List<MusicDTO> getDefaultPlayList(String id) {
        return sqlSession.selectList("music.getDefaultPlayList", id);
    }

    @Override
    public int likeMusic(FavoriteDTO favoriteDTO) {
        return sqlSession.insert("music.likeMusic", favoriteDTO);
    }

    @Override
    public int unlikeMusic(FavoriteDTO favoriteDTO) {
        return sqlSession.delete("music.unlikeMusic", favoriteDTO);
    }

    @Override
    public int isLikedMusic(FavoriteDTO favoriteDTO) {
        return sqlSession.selectOne("music.isLikedMusic", favoriteDTO);
    }
}

