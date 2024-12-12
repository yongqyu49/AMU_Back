package com.myspringweb.amu_back.domain.dao;

import com.myspringweb.amu_back.domain.dto.FavoriteDTO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;

import java.util.List;

public interface MusicDAO {
    int uploadMusic(MusicDTO MusicDTO); //MusicDTO에서 MusicDTO클래스

    List<MusicDTO> getAllMusic();

    MusicDTO getMusicById(int id);

    List<ReviewDTO> getReviewByMusicCode(int musicCode);

    int uploadReview(ReviewDTO reviewDTO);

    List<MusicDTO> getAllMusicLatest();

    int likeMusic(FavoriteDTO favoriteDTO);

    int unlikeMusic(FavoriteDTO favoriteDTO);

    int isLikedMusic(FavoriteDTO favoriteDTO);

    String getImgPathByMusicCode(int musicCode);

    List<MusicDTO> getAllMusicSorted(String sortType);

    List<ReviewDTO> getMusicReviewList(int musicCode);

    int getMusicReviewCounts(int musicCode);

    List<MusicDTO> searchMusic(String query);

    int updateViews(int musicCode);

    int deleteMusic(int musicCode);
}
