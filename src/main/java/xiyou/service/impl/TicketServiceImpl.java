package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiyou.dao.*;
import xiyou.pojo.*;
import xiyou.service.TicketService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private SaleItemMapper saleItemMapper;
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    PlayMapper playMapper;

    @Transactional
    @Override
    public int insert(Ticket ticket) {
        return ticketMapper.insertSelective(ticket);
    }

    @Transactional
    @Override
    public int update(Ticket ticket) {
        return ticketMapper.updateByPrimaryKeySelective(ticket);
    }

    @Transactional
    @Override
    public int delete(long ticketId) {
        return ticketMapper.deleteByPrimaryKey(ticketId);
    }

    @Override
    public List<Ticket> selectAll() {
        return ticketMapper.selectByExample(null);
    }

    @Override
    public List<Ticket> selectBySchedId(int schedId) {
        return ticketMapper.selectBySchedId(schedId);
    }

    @Override
    public Ticket selectByPrimaryKey(long ticketId) {
        return ticketMapper.selectByPrimaryKey(ticketId);
    }

    @Transactional
    @Override
    public int updataTicketStatus(Integer status, Integer schedId, Integer seatId) {
        return ticketMapper.updateTicketStatus(status,schedId,seatId);
    }

    @Transactional
    @Override
    public int buyTickets(List<Ticket> tickets,Integer emp_id) {
        int k=0;
        for(int i=0;i<tickets.size();i++)
        {
            k = k+updataTicketStatus(Integer.valueOf(tickets.get(i).getTicketStatus()),tickets.get(i).getSchedId(),tickets.get(i).getSeatId());
        }

        List<Ticket> list = new ArrayList<>();
        for(int i=0;i<tickets.size();i++)
        {
            list.add(ticketMapper.selectBySchedIdSeatId(tickets.get(i).getSchedId(),tickets.get(i).getSeatId()));
        }
        Sale sale=null;
        if(list.get(0)!=null)
        {
            sale = new Sale();
            sale.setEmpId(emp_id);
            sale.setSaleChange(BigDecimal.ZERO);
            sale.setSalePayment(list.get(0).getTicketPrice().multiply(new BigDecimal(tickets.size())));
            sale.setSaleStatus((short) 1);
            sale.setSaleType((short) 1);
            sale.setSaleTime(new Date());
        }
        if(sale!=null)
        {
            saleMapper.insertSelective(sale);
            for(int i=0;i<list.size();i++)
            {
                SaleItem saleItem = new SaleItem();
                saleItem.setTicketId(list.get(i).getTicketId());
                saleItem.setSaleItemPrice(new BigDecimal(String.valueOf(list.get(i).getTicketPrice())));
                saleItem.setSaleId(sale.getSaleId());
                saleItemMapper.insertSelective(saleItem);
            }
        }

        return k;
    }

    @Transactional
    @Override
    public int lockTickets(List<Ticket> tickets) {
        int flag =1;
        List<Ticket> ticketList = new ArrayList<>();
        for(int i=0;i<tickets.size();i++)
        {
            Ticket t = ticketMapper.selectBySchedIdSeatId(tickets.get(i).getSchedId(),tickets.get(i).getSeatId());
            if (t.getTicketLockedTime()!=null&&t.getTicketLockedTime().getTime()+10*60*1000>new Date().getTime())
            {
                flag= 0;
                break;
            }
            ticketList.add(t);
            t.setTicketLockedTime(new Date());
        }
        if(flag==1)
        {
            for(int i=0;i<ticketList.size();i++)
            {
                ticketMapper.updateByPrimaryKeySelective(ticketList.get(i));
            }
            return 1;
        }
        return 0;
    }

    @Transactional
    @Override
    public int unlockTickets(List<Ticket> tickets) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<tickets.size();i++)
        {
            Ticket t = ticketMapper.selectBySchedIdSeatId(tickets.get(i).getSchedId(),tickets.get(i).getSeatId());
            try {
                t.setTicketLockedTime(sdf.parse("1997-02-06"));
            } catch (ParseException e) {
                return 0;
            }
            ticketMapper.updateByPrimaryKeySelective(t);
        }
        return 1;
    }

    @Override
    public List<Ticket> selectTicketByTicket(List<Ticket> tickets) {
        List<Ticket> list = new ArrayList<>();
        for(int i=0;i<tickets.size();i++)
        {
            list.add(ticketMapper.selectBySchedIdSeatId(tickets.get(i).getSchedId(),tickets.get(i).getSeatId()));
        }
        return list;
    }

    @Override
    public int refundTicket(Long saleId) {
        List<SaleItem> saleItems = saleItemMapper.selectBySaleId(saleId);
        if (saleItems.size()<=0)
        {
            return 0;
        }
        Ticket ticket1 =null;
        ticket1 = ticketMapper.selectByPrimaryKey(saleItems.get(0).getTicketId());
        int scId1 = ticket1.getSchedId();
        Schedule schedule1 = scheduleMapper.selectByPrimaryKey(scId1);
        if (schedule1.getSchedTime().getTime() -15*60*1000<new Date().getTime() )
        {
            return 0;
        }
        int k=0;
        Sale sale = saleMapper.selectByPrimaryKey(saleId);
        sale.setSaleId(null);
        sale.setSaleStatus((short) 1);
        sale.setSaleType((short) -1);
        sale.setSaleTime(new Date());
        saleMapper.insertSelective(sale);


        for(int i=0;i<saleItems.size();i++)
        {
            Ticket ticket =null;
            ticket = ticketMapper.selectByPrimaryKey(saleItems.get(i).getTicketId());
            if(ticket!=null)
            {
                ticket.setTicketStatus((short) 0);
                k =ticketMapper.updateByPrimaryKeySelective(ticket);
            }
        }
        return k;
    }


}
