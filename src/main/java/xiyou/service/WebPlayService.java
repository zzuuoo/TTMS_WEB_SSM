package xiyou.service;

import xiyou.pojo.Play;

import java.util.List;

public interface WebPlayService {
    Play selectByPrimaryKey(int playId);

    List<Play> selectAllPlay();
}
