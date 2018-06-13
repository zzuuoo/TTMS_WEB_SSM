package xiyou.service;

import xiyou.pojo.Ticket;

import java.util.List;

public interface TicketService {

    int insert(Ticket ticket);
    int update(Ticket ticket);
    int delete(long ticketId);
    List<Ticket> selectAll();
    List<Ticket> selectBySchedId(int schedId);
    Ticket selectByPrimaryKey(long ticketId);
    int updataTicketStatus(Integer status,Integer schedId,Integer seatId);
    int buyTickets(List<Ticket> tickets,Integer emp_id);
    int lockTickets(List<Ticket> tickets);
    int unlockTickets(List<Ticket> tickets);
    List<Ticket> selectTicketByTicket(List<Ticket> tickets);
    int refundTicket(Long saleId);

}
