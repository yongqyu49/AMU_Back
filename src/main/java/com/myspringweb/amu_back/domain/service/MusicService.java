package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.FavoriteDTO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;

import java.util.List;

public interface MusicService {
    boolean uploadMusic(MusicDTO musicDTO);

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

    List<ReviewDTO> getMusicReviewList(int i);

    int getMusicReviewCounts(int musicCode);

    List<MusicDTO> searchMusic(String query);

    int updateViews(int musicCode);
}
