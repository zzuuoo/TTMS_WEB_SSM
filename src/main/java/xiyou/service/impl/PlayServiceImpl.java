package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.PlayMapper;
import xiyou.pojo.Play;
import xiyou.service.PlayService;

import java.util.List;

@Service
public class PlayServiceImpl implements PlayService{

    @Autowired
    private PlayMapper playMapper;



    public Play selectByPrimaryKey(int playId) {
        return playMapper.selectByPrimaryKey(playId);
    }

    @Override
    public int deletePlayById(int playId) {
        return playMapper.deleteByPrimaryKey(playId);
    }

    @Override
    public List<Play> getPlay() {
        return playMapper.selectByExample(null);
    }

    @Override
    public int insertPlay(Play play) {
        return playMapper.insertSelective(play);
    }

    @Override
    public int updatePlay(Play play) {
        return playMapper.updateByPrimaryKeySelective(play);
    }

    @Override
    public List<Play> getVaiPlay() {
        return playMapper.selectVaiPlay(0);
    }
}
