package xiyou.service;

import xiyou.pojo.Studio;

import java.util.List;

public interface StudioService {
    Studio selectByPrimaryKey(int studioId);
    public int deleteByPrimary(int studioId);
    public int updateByPrimary(Studio studio);
    public int insertSelective(Studio studio);
    public  int insertReturnKey(String s);
    public List<Studio> selectAll();
}
