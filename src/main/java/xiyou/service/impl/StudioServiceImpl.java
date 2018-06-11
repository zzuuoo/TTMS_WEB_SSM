package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.SeatMapper;
import xiyou.dao.StudioMapper;
import xiyou.pojo.Seat;
import xiyou.pojo.Studio;
import xiyou.service.StudioService;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService{

    @Autowired
    private StudioMapper studioMapper;

    @Autowired
    private SeatMapper seatMapper;
    @Override
    public Studio selectByPrimaryKey(int studioId) {
        return studioMapper.selectByPrimaryKey(studioId);
    }



    @Override
    public int deleteByPrimary(int studioId)
    {
        //数据库设置了级联删除，所以不用写删除座位逻辑
        return studioMapper.deleteByPrimaryKey(studioId);
    }
    @Override
    public int updateByPrimary(Studio studio)
    {
        Studio s =  studioMapper.selectByPrimaryKey(studio.getStudioId());

        if(s.getStudioColCount()!=studio.getStudioColCount()||s.getStudioRowCount()!=studio.getStudioRowCount())
        {
            seatMapper.deleteByStudioId(s.getStudioId());
            int r= studio.getStudioRowCount();
            int c =studio.getStudioColCount();
            int id = s.getStudioId();

                    for(int i=1;i<=r;i++)
                    {
                        for(int j=1;j<=c;j++)
                        {
                            Seat se  = new Seat();
                            se.setSeatColumn(j);
                            se.setSeatRow(i);
                            se.setSeatStatus((short) 1);
                            se.setStudioId(id);
                            seatMapper.insertSelective(se);
                        }
                    }
        }

        return studioMapper.updateByPrimaryKey(studio);

    }
    @Override
    public int insertSelective(Studio studio)
    {
        int in = studioMapper.insertSelective(studio);
        Studio s =  studioMapper.selectByPrimaryKey(studio.getStudioId());
        int r= s.getStudioRowCount();
        int c =s.getStudioColCount();
//        System.out.println("插入行"+r+"列"+c);
        for(int i=1;i<=r;i++)
        {
            for(int j=1;j<=c;j++)
            {
                Seat seat = new Seat();
                seat.setSeatColumn(j);
                seat.setSeatRow(i);
                seat.setSeatStatus((short) 1);
                seat.setStudioId(s.getStudioId());
//                System.out.println("插入座位到"+in);
                seatMapper.insertSelective(seat);
            }
        }
        return  in;
    }

    @Override
    public int insertReturnKey(String s) {
        return 0;
    }

    @Override
    public List<Studio> selectAll()
    {
        return studioMapper.selectByExample(null);
    }

}
