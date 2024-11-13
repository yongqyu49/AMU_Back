package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.UserDAO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(String id) {
        return userDAO.getUserById(id);
    }

    @Override
    public int signUp(UserDTO userDTO) {
        int result = 0;
        if (userDAO.getUserById(userDTO.getId()) != null) {
            throw new DuplicateKeyException("이미 존재하는 아이디입니다.");
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            if (userDAO.getUserById(userDTO.getId()) == null) {
                result = userDAO.signUp(userDTO);
            } else {
                System.out.println("signUp: 실패");
            }
        }
        return result;
    }

}
