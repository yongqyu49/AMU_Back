package com.myspringweb.amu_back.domain.dao;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;

import java.util.List;

public interface UserDAO {
    UserDTO getUserById(String id);

    int signUp(UserDTO userDTO);

    int updateProfile(UserDTO userDTO);

    List<MusicDTO> getMyUploadList(String id);

    List<MusicDTO> getMyFavoriteList(String id);

    List<ReviewDTO> getMyReviewList(String id);

    List<PlaylistDTO> getMyPlaylistList(String id);
}
