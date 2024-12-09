package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.MusicDAO;
import com.myspringweb.amu_back.domain.dto.FavoriteDTO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicDAO musicDAO;

    @Override
    public boolean uploadMusic(MusicDTO musicDTO){
        int result = musicDAO.uploadMusic(musicDTO);
        return result > 0;
    }

    @Override
    public List<MusicDTO> getAllMusic() {
        return musicDAO.getAllMusic();
    }

    @Override
    public MusicDTO getMusicById(int id) {
        return musicDAO.getMusicById(id);
    }

    @Override
    public List<ReviewDTO> getReviewByMusicCode(int musicCode) {
        return musicDAO.getReviewByMusicCode(musicCode);
    }

    @Override
    public int uploadReview(ReviewDTO reviewDTO) {
        return musicDAO.uploadReview(reviewDTO);
    }

    @Override
    public List<MusicDTO> getAllMusicLatest() {
        return musicDAO.getAllMusicLatest();
    }

    @Override
    public List<MusicDTO> getDefaultPlaylist(String id) {
        return musicDAO.getDefaultPlayList(id);
    }

    @Override
    public int likeMusic(FavoriteDTO favoriteDTO) {
        return musicDAO.likeMusic(favoriteDTO);
    }

    @Override
    public int unlikeMusic(FavoriteDTO favoriteDTO) {
        return musicDAO.unlikeMusic(favoriteDTO);
    }

    @Override
    public int isLikedMusic(FavoriteDTO favoriteDTO) {
        return musicDAO.isLikedMusic(favoriteDTO);
    }


}
