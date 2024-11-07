package com.myspringweb.amu_back.domain.service;

import com.myspringweb.amu_back.domain.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(String id);
}
