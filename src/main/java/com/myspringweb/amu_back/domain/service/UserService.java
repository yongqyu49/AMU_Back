package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    int signUp(UserDTO userDTO);

    UserDTO signIn(UserDTO userDTO);

    int updateProfile(UserDTO userDTO);

    UserDTO getUserById(String id);

    List<MusicDTO> getMyUploadList(String id);

    List<MusicDTO> getMyFavoriteList(String id);

    List<ReviewDTO> getMyReviewList(String id);
}
