package xiyou.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xiyou.pojo.Ticket;
import xiyou.pojo.TicketExample;

public interface TicketMapper {
    long countByExample(TicketExample example);

    int deleteByExample(TicketExample example);

    int deleteByPrimaryKey(Long ticketId);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    List<Ticket> selectByExample(TicketExample example);

    Ticket selectByPrimaryKey(Long ticketId);

    int updateByExampleSelective(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByExample(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    List<Ticket> selectBySchedId(int schedId);

    int deleteBySchedId(int schedId);
    int updatePriceByschedId(Ticket record);
    int updateTicketStatus(Integer status,Integer sched_id,Integer seat_id);
    Ticket selectBySchedIdSeatId(Integer schedId,Integer seatId);
}