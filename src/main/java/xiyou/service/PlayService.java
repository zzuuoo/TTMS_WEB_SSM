package xiyou.service;

import xiyou.pojo.Play;

import java.util.List;

public interface PlayService {
    Play selectByPrimaryKey(int playId);
    int deletePlayById(int playId);
    List<Play> getPlay();
    int insertPlay(Play play);
    int updatePlay(Play play);
    List<Play> getVaiPlay();
}
