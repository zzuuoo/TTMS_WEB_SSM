package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.SeatMapper;
import xiyou.pojo.Seat;
import xiyou.pojo.SeatExample;
import xiyou.service.SeatService;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{
    @Autowired
    private SeatMapper seatMapper;
    @Override
    public List<Seat> getSeatByStudioId(Integer studioId) {
        return seatMapper.selectByStudioId(studioId);
    }

    @Override
    public List<Seat> getAll() {
        return seatMapper.selectByExample(null);
    }

    @Override
    public int deleteByPrimary(int seatId) {
        return seatMapper.deleteByPrimaryKey(seatId);
    }

    @Override
    public int updateByPrimary(Seat seat) {
        return seatMapper.updateByPrimaryKeySelective(seat);
    }


    @Override
    public int deleteByStudioId(Integer studioId) {
        return seatMapper.deleteByStudioId(studioId);
    }

    @Override
    public int insertSelective(Seat seat) {
        return seatMapper.insertSelective(seat);
    }
}
