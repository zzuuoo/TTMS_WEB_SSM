package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.PlayMapper;
import xiyou.pojo.Play;
import xiyou.service.PlayService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/mobilePlay")
public class MobilePlayController {

    @Autowired
    private PlayService playService;

    @ResponseBody
    @RequestMapping(value ="getPlayById",method = RequestMethod.GET)
    public Play getPlayById(@RequestParam int play_id)
    {
        return playService.selectByPrimaryKey(play_id);
    }
    @ResponseBody
    @RequestMapping(value = "getVaiPlay",method = RequestMethod.GET)
    public List<Play> getVaiPlay()
    {
        return playService.getVaiPlay();
    }

    @ResponseBody
    @RequestMapping(value = "getPlay",method = RequestMethod.GET)
    public List<Play> getPlay()
    {
        return playService.getPlay();
    }

    @ResponseBody
    @RequestMapping(value = "deletePlayById",method = RequestMethod.GET)
    public String deletePlayById(@RequestParam int play_id)
    {
        if(playService.deletePlayById(play_id)>0)
        {
            return "succeed";
        }
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "insertPlay",method = RequestMethod.POST)
    public String insertPlay(@RequestBody Play play)
    {
        if(playService.insertPlay(play)>0)
            return "succeed";
        return "failed";
    }

    @ResponseBody
    @RequestMapping(value = "updatePlay",method = RequestMethod.POST)
    public String updatePlay(@RequestBody Play play)
    {
        if(playService.updatePlay(play)>0)
        {
            return "succeed";
        }

        return "failed";
    }

}
