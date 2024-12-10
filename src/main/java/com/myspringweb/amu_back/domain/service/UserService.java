package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.UserDTO;

public interface UserService {
    int signUp(UserDTO userDTO);

    UserDTO signIn(UserDTO userDTO);

    int updateProfile(UserDTO userDTO);

    UserDTO getUserById(String id);
}
