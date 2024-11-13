package com.myspringweb.amu_back.domain.dao;

import com.myspringweb.amu_back.domain.dto.UserDTO;

public interface UserDAO {
    UserDTO getUserById(String id);
    int signUp(UserDTO userDTO);
//    int signIn(UserDTO userDTO);
}
