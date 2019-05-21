package xiyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.pojo.Seat;
import xiyou.service.SeatService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("seat/")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @ResponseBody
    @RequestMapping(value = "getSeatByStudioId",method = RequestMethod.GET)
    public List<Seat> getSeatByStudioId(@RequestParam Integer studio_id)
    {
        return seatService.getSeatByStudioId(studio_id);
    }


}
