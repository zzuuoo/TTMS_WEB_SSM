package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.pojo.Seat;
import xiyou.service.SeatService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/mobileSeat")
public class MobileSeatController {

    @Autowired
    private SeatService seatService;

    @ResponseBody
    @RequestMapping(value = "/getSeatByStudioId",method = RequestMethod.GET)
    public List<Seat> getSeatByStudioId(@RequestParam int studioId)
    {
        return seatService.getSeatByStudioId(studioId);
    }
    @ResponseBody
    @RequestMapping(value = "/getSeat",method = RequestMethod.GET)
    public List<Seat> getSeat()
    {
        return seatService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/updateByPrimaryKey",method = RequestMethod.POST)
    public String updateByPrimaryKey(@RequestBody List<Seat> seats)
    {
        int k = 0;
        for(int i=0;i<seats.size();i++)
        {
            k = seatService.updateByPrimary(seats.get(i));
        }
        if(k>0)
        {
            return "succeed";
        }
        return "failed";
    }





}
