package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.UserDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import com.myspringweb.amu_back.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserDTO signIn(UserDTO userDTO) {
        UserDTO user = userDAO.getUserById(userDTO.getId());

        if (user == null)
            return null; // 사용자가 없으면 null 반환

        // 비밀번호 일치 확인
        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword()))
            return user; // 비밀번호가 일치하면 사용자 정보 반환
        else
            return null; // 비밀번호가 일치하지 않으면 null 반환
    }

    @Override
    public int updateProfile(UserDTO userDTO) {
        return userDAO.updateProfile(userDTO);
    }

    @Override
    public UserDTO getUserById(String id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<MusicDTO> getMyUploadList(String id) {
        return userDAO.getMyUploadList(id);
    }

    @Override
    public List<MusicDTO> getMyFavoriteList(String id) {
        return userDAO.getMyFavoriteList(id);
    }

    @Override
    public List<ReviewDTO> getMyReviewList(String id) {
        return userDAO.getMyReviewList(id);
    }


}
