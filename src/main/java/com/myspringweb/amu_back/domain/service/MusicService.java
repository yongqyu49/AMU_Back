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

    List<MusicDTO> getDefaultPlaylist(String id);

    int likeMusic(FavoriteDTO favoriteDTO);

    int unlikeMusic(FavoriteDTO favoriteDTO);

    int isLikedMusic(FavoriteDTO favoriteDTO);
}
