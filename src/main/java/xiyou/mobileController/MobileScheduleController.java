package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.ScheduleMapper;
import xiyou.pojo.Schedule;
import xiyou.service.ScheduleService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/mobileSchedule")
public class MobileScheduleController {
    @Autowired
    private ScheduleService scheduleService;


    @ResponseBody
    @RequestMapping(value ="getScheduleById",method = RequestMethod.GET)
    public Schedule getScheduleById(@RequestParam int sched_id)
    {
        return scheduleService.selectByPrimaryKey(sched_id);
    }
    @ResponseBody
    @RequestMapping(value = "getSchedule",method = RequestMethod.GET)
    public List<Schedule> getSchedule()
    {
        return scheduleService.selectAll();
    }

    @ResponseBody
    @RequestMapping(value = "deleteScheduleById",method = RequestMethod.GET)
    public String deleteScheduleById(@RequestParam int schedule_id)
    {
        if(scheduleService.delete(schedule_id)>0)
        {
            return "succeed";
        }
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "insertSchedule",method = RequestMethod.POST)
    public String insertSchedule(@RequestBody Schedule schedule)
    {
        if(scheduleService.insert(schedule)>0)
            return "succeed";
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "updateSchedule",method = RequestMethod.POST)
    public String updateSchedule(@RequestBody Schedule schedule)
    {
        if(scheduleService.update(schedule)>0)
            return "succeed";
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "getScheduleByPlayId",method = RequestMethod.GET)
    public List<Schedule> getScheduleByPlayId(@RequestParam int play_id)
    {
        return scheduleService.selectByPlayId(play_id);
    }

}
