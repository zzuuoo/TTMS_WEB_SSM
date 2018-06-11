package xiyou.service;

import xiyou.pojo.Seat;

import java.util.List;

public interface SeatService {
    public List<Seat> getSeatByStudioId(Integer studioId);
    public List<Seat> getAll();
    public int deleteByPrimary(int seatId);
    public int updateByPrimary(Seat seat);
    public int deleteByStudioId(Integer StudioId);
    public int insertSelective(Seat seat);
}
