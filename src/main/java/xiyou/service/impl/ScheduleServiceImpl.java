package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiyou.dao.*;
import xiyou.pojo.*;
import xiyou.service.ScheduleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private PlayMapper playMapper;
    @Autowired
    private StudioMapper studioMapper;

    @Transactional
    @Override
    public int insert(Schedule schedule) {
        int in = scheduleMapper.insertSelective(schedule);
        List<Seat> seats = seatMapper.selectByStudioId(schedule.getStudioId());
        Ticket ticket;
        if(in>0)//studio / play状态+1
        {
            for(int i=0;i<seats.size();i++)
            {
                ticket = new Ticket();
                ticket.setSchedId(schedule.getSchedId());
                ticket.setSeatId(seats.get(i).getSeatId());
                ticket.setTicketPrice(schedule.getSchedTicketPrice());
                ticket.setTicketStatus((short) 0);
                ticketMapper.insertSelective(ticket);
            }
//            studioMapper.updateByPrimaryKeySelective();
            Studio studio = studioMapper.selectByPrimaryKey(schedule.getStudioId());
            studio.setStudioFlag((short) (studio.getStudioFlag()+1));
            studioMapper.updateByPrimaryKeySelective(studio);
            Play play = playMapper.selectByPrimaryKey(schedule.getPlayId());
            play.setPlayStatus((short) (play.getPlayStatus()+1));
            playMapper.updateByPrimaryKeySelective(play);
        }
        return in;
    }

    @Transactional
    @Override
    public int update(Schedule schedule) {
        Schedule sc = scheduleMapper.selectByPrimaryKey(schedule.getSchedId());
        int in = scheduleMapper.updateByPrimaryKeySelective(schedule);
        if(in>0)
        {
            if(sc.getStudioId()!=schedule.getStudioId()||!sc.getSchedTicketPrice().equals(schedule.getSchedTicketPrice()))
            {
                ticketMapper.deleteBySchedId(sc.getSchedId());
                List<Seat> seats = seatMapper.selectByStudioId(schedule.getStudioId());
                Ticket ticket;
                for(int i=0;i<seats.size();i++)
                {
                    ticket = new Ticket();
                    ticket.setSchedId(schedule.getSchedId());
                    ticket.setSeatId(seats.get(i).getSeatId());
                    ticket.setTicketPrice(schedule.getSchedTicketPrice());
                    ticket.setTicketStatus((short) 0);
                    ticketMapper.insertSelective(ticket);
                }
            }
            if (sc.getPlayId()!=schedule.getPlayId())
            {
                Play play = playMapper.selectByPrimaryKey(sc.getPlayId());
                play.setPlayStatus((short) (play.getPlayStatus()-1));
                playMapper.updateByPrimaryKeySelective(play);
                play = playMapper.selectByPrimaryKey(schedule.getPlayId());
                play.setPlayStatus((short) (play.getPlayStatus()+1));
                playMapper.updateByPrimaryKeySelective(play);
            }
            if(sc.getStudioId()!=schedule.getStudioId())
            {
                Studio studio = studioMapper.selectByPrimaryKey(sc.getStudioId());
                studio.setStudioFlag((short) (studio.getStudioFlag()-1));
                studioMapper.updateByPrimaryKeySelective(studio);
                studio = studioMapper.selectByPrimaryKey(schedule.getStudioId());
                studio.setStudioFlag((short) (studio.getStudioFlag()+1));
                studioMapper.updateByPrimaryKeySelective(studio);
            }
        }

        return in;
    }

    @Transactional
    @Override
    public int delete(int schedId) {//数据库级联删除
        Schedule schedule = scheduleMapper.selectByPrimaryKey(schedId);
        int in = scheduleMapper.deleteByPrimaryKey(schedId);
        if(in>0)
        {
            Studio studio = studioMapper.selectByPrimaryKey(schedule.getStudioId());
            studio.setStudioFlag((short) (studio.getStudioFlag()-1));
            studioMapper.updateByPrimaryKeySelective(studio);
            Play play = playMapper.selectByPrimaryKey(schedule.getPlayId());
            play.setPlayStatus((short) (play.getPlayStatus()-1));
            playMapper.updateByPrimaryKeySelective(play);
        }
        return in;
    }

    @Override
    public List<Schedule> selectAll() {
        return scheduleMapper.selectByExample(null);
    }

    @Override
    public Schedule selectByPrimaryKey(int schedId) {
        return scheduleMapper.selectByPrimaryKey(schedId);
    }

    @Override
    public List<Schedule> selectByPlayId(int playId) {
        List<Schedule> schedules = new ArrayList<>();
        List<Schedule> list = scheduleMapper.selectByPlayId(playId);
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getSchedTime().getTime()>new Date().getTime()+10*60*1000)
            {
                schedules.add(list.get(i));
            }
        }
        return schedules;
    }
}
