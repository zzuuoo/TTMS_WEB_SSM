package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.PlayMapper;
import xiyou.pojo.Play;
import xiyou.service.WebPlayService;
@Service
public class WebPlayServiceImpl implements WebPlayService {

    @Autowired
    private PlayMapper playMapper;

    public Play selectByPrimaryKey(int playId) {
        return playMapper.selectByPrimaryKey(playId);
    }
}
