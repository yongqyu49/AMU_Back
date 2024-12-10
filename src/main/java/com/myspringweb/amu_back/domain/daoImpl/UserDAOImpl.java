package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.UserDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final SqlSession sqlSession;

    @Override
    public UserDTO getUserById(String id) {
        return sqlSession.selectOne("user.getUserById", id);
    }

    @Override
    public int signUp(UserDTO userDTO) {
        return sqlSession.insert("user.signUp", userDTO);
    }

    @Override
    public int updateProfile(UserDTO userDTO) {
        return sqlSession.update("user.updateProfile", userDTO);
    }

    @Override
    public List<MusicDTO> getMyUploadList(String id) {
        return sqlSession.selectList("user.getMyUploadList", id);
    }

}
