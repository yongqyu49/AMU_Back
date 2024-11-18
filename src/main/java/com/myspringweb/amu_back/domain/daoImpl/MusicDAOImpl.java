package com.myspringweb.amu_back.domain.daoImpl;

import com.myspringweb.amu_back.domain.dao.MusicDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor //@RequiredArgsConstructor: Lombok 어노테이션을 사용하면 final 붙은 필드 생성자 자동 생성
public class MusicDAOImpl implements MusicDAO {
    private final SqlSession sqlSession;

    @Override
    public int uploadMusic(MusicDTO musicDTO) {
        return sqlSession.insert("music.updateMusic", musicDTO);
    }
}

