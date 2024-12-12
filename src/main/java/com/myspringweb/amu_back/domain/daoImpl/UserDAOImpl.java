package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.UserDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.PlaylistDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import com.myspringweb.amu_back.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final SqlSession sqlSession;

    @Override
    public UserDTO getUserById(String id) {
        return sqlSession.selectOne("user.getUserById", id);
    }

    @Transactional // 트랜잭션 적용
    public int signUp(UserDTO userDTO) {
        try {
            sqlSession.insert("user.signUp", userDTO);
            sqlSession.insert("user.insertPlaylist", userDTO);
            return 1;
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 오류 발생", e); // 강제 롤백
        }
    }

    @Override
    public int updateProfile(UserDTO userDTO) {
        return sqlSession.update("user.updateProfile", userDTO);
    }

    @Override
    public List<MusicDTO> getMyUploadList(String id) {
        return sqlSession.selectList("user.getMyUploadList", id);
    }

    @Override
    public List<MusicDTO> getMyFavoriteList(String id) {
        return sqlSession.selectList("user.getMyFavoriteList", id);
    }

    @Override
    public List<ReviewDTO> getMyReviewList(String id) {
        return sqlSession.selectList("user.getMyReviewList", id);
    }

    @Override
    public List<PlaylistDTO> getMyPlaylistList(String id) {
        return sqlSession.selectList("user.getMyPlaylistList", id);
    }

}
