package com.myspringweb.amu_back.domain.serviceImpl;

import com.myspringweb.amu_back.domain.dao.MusicDAO;
import com.myspringweb.amu_back.domain.dto.MusicDTO;
import com.myspringweb.amu_back.domain.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicDAO musicDAO;

    @Override
    public boolean uploadMusic(MusicDTO musicDTO){
        int result = musicDAO.uploadMusic(musicDTO);
        return result > 0;
    }
}
