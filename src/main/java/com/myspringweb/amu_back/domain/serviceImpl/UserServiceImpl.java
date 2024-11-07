package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.UserDAO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public UserDTO getUserById(String id) {
        return userDAO.getUserById(id);
    }
}
