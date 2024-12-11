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
        try {
            System.out.println("=== Service 레벨 시작 ===");
            int result = musicDAO.uploadMusic(musicDTO);
            System.out.println("DAO 결과: " + result);
            return result > 0;
        } catch (Exception e) {
            System.out.println("=== Service 레벨 에러 ===");
            System.out.println("에러 메시지: " + e.getMessage()); 
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<MusicDTO> getAllMusic() {
        List<MusicDTO> list = musicDAO.getAllMusic();
        System.out.println("Service - musicList: " + list);
        return list;
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

    @Override
    public String getImgPathByMusicCode(int musicCode) {
        System.out.println("=== Service 이미지 경로 조회 ===");
        System.out.println("조회할 musicCode: " + musicCode);
        System.out.println("조회된 이미지 경로: " + musicDAO.getImgPathByMusicCode(musicCode));
        return  musicDAO.getImgPathByMusicCode(musicCode);
    }

    @Override
    public List<MusicDTO> getAllMusicSorted(String sortType) {
        return musicDAO.getAllMusicSorted(sortType);
    }

}
