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

    @Override
    public String getImgPathByMusicCode(int musicCode) {
        System.out.println("=== DAO 이미지 경로 조회 ===");
        System.out.println("조회할 musicCode: " + musicCode);
        System.out.println("조회된 이미지 경로: " + sqlSession.selectOne("music.getImgPathByMusicCode", musicCode));
        return sqlSession.selectOne("music.getImgPathByMusicCode", musicCode);
    }

    @Override
    public List<MusicDTO> getAllMusicSorted(String sortType) {
        return sqlSession.selectList("music.getAllMusicSorted", sortType);
    }

    @Override
    public List<ReviewDTO> getMusicReviewList(int musicCode) {
        return sqlSession.selectList("music.getMusicReviewList", musicCode);
    }

    @Override
    public int getMusicReviewCounts(int musicCode) {
        return sqlSession.selectOne("music.getMusicReviewCounts", musicCode);
    }

    @Override
    public List<MusicDTO> searchMusic(String query) {
        return sqlSession.selectList("music.searchMusic", query);
    }

    @Override
    public int updateViews(int musicCode) {
        return sqlSession.update("music.updateViews", musicCode);
    }

    @Override
    public int deleteMusic(int musicCode) {
        return sqlSession.delete("music.deleteMusic", musicCode);
    }
}

