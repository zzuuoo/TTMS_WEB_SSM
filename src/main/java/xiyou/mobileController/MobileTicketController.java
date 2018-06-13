package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.pojo.Ticket;
import xiyou.service.TicketService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/mobileTicket")
public class MobileTicketController {


    @Autowired
    private TicketService ticketService;

    @ResponseBody
    @RequestMapping("getTicket")
    private List<Ticket> getTicket()
    {
        return ticketService.selectAll();
    }

    /**
     * 通过演出id查询票
     * @param sched_id
     * @return
     */
    @ResponseBody
    @RequestMapping("getTicketBySchedId")
    private List<Ticket>  getTicketBySchedId(@RequestParam int sched_id)
    {
        return ticketService.selectBySchedId(sched_id);
    }

//    @ResponseBody
//    @RequestMapping("getTicketBySchedId")
//    private List<Ticket>  getTicketBySchedId(@RequestParam int sched_id)
//    {
//        return ticketService.selectBySchedId(sched_id);
//    }

    @ResponseBody
    @RequestMapping("getTicketById")
    private Ticket  getTicketById(@RequestParam long ticket_id)
    {
        return ticketService.selectByPrimaryKey(ticket_id);
    }

    @ResponseBody
    @RequestMapping("deleteTicketById")
    private String  deleteTicketById(@RequestParam long ticket_id)
    {
        if (ticketService.delete(ticket_id)>0)
            return "success";
        return "failed";
    }
    @ResponseBody
    @RequestMapping(value = "insertTicket",method = RequestMethod.POST)
    private String insertTicket(@RequestBody Ticket ticket)
    {
        if(ticketService.insert(ticket)>0)
        {
            return "succeed";
        }
        return "failed";
    }

    /**
     * Post方法请求跟新ticket
     * 请求时Ajax字符串必须拼接成‘{user:"aa",pass:"pppp"}'的形式
     * @param ticket
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateTicket",method = RequestMethod.POST)
    private String updateTicket(@RequestBody  Ticket ticket)
    {
        if(ticketService.update(ticket)>0)
        {
            return "succeed";
        }
        return "failed";
    }
    @ResponseBody
    @RequestMapping(value = "updateTicketList",method = RequestMethod.POST)
    private String updateTicketList(@RequestBody  List<Ticket> tickets,@RequestParam Integer emp_id)
    {
        int k =ticketService.buyTickets(tickets,emp_id);

        System.out.println("我是id:"+emp_id);
        if(k>0)
        {
            System.out.println(k);
            return "succeed";
        }
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "selectTicketListBytickets",method = RequestMethod.POST)
    private List<Ticket> selectTicketListBytickets(@RequestBody  List<Ticket> ticket)
    {
        List<Ticket> ticketList = ticketService.selectTicketByTicket(ticket);
        return ticketList;
    }
    @ResponseBody
    @RequestMapping(value = "lockTicketList",method = RequestMethod.POST)
    private String lockTicketList(@RequestBody List<Ticket> tickets)
    {
        if(ticketService.lockTickets(tickets)>0)
        {
            return "succeed";
        }
        return "failed";
    }
    @ResponseBody
    @RequestMapping(value = "unlockTicketList",method = RequestMethod.POST)
    private String unlockTicketList(@RequestBody List<Ticket> tickets)
    {
        if(ticketService.unlockTickets(tickets)>0)
        {
            return "succeed";
        }
        return "failed";
    }
    @ResponseBody
    @RequestMapping(value = "refundTcket",method = RequestMethod.GET)
    private String refundTcket(@RequestParam Long SaleId){
        if(ticketService.refundTicket(SaleId)>0)
        {
            return "succeed";
        }
        return "failed";
    }


}
