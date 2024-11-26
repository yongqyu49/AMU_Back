package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.MusicDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //@RequiredArgsConstructor: Lombok 어노테이션을 사용하면 final 붙은 필드 생성자 자동 생성
public class MusicDAOImpl implements MusicDAO {
    private final SqlSession sqlSession;

    @Override
    public int uploadMusic(MusicDTO musicDTO) {
        return sqlSession.insert("music.updateMusic", musicDTO);
    }

    @Override
    public List<MusicDTO> getAllMusic() {
        return sqlSession.selectList("music.getAllMusic");
    }

    @Override
    public MusicDTO getMusicById(int id) {
        return sqlSession.selectOne("music.getMusicById", id);
    }

    @Override
    public List<ReviewDTO> getReviewByMusicCode(int musicCode) {
        return sqlSession.selectList("music.getReviewByMusicCode", musicCode);
    }

    @Override
    public int uploadReview(ReviewDTO reviewDTO) {
        int result = sqlSession.insert("music.uploadReview", reviewDTO);
        System.out.println("업로드??  " + result);
        return result;
    }
}

