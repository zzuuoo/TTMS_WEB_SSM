package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.StudioMapper;
import xiyou.pojo.Studio;
import xiyou.service.StudioService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/mobileStudio")
public class MobileStudioController {

    @Autowired
    private StudioService studioService;

    @RequestMapping(value = "/getStudio")
    @ResponseBody
    public List<Studio> getStudio()
    {
        return  studioService.selectAll();
    }

    @ResponseBody
    @RequestMapping(value = "/getStudioById")
    public Studio getStudioById(@RequestParam int studio_id)
    {
        return studioService.selectByPrimaryKey(studio_id);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteStudio")
    public String deleteStudio(@RequestParam int studio_id)
    {
        if(studioService.deleteByPrimary(studio_id)>0)
        {
            return "succeed";
        }
        return "faild";
    }

    @ResponseBody
    @RequestMapping(value = "/insertStudio")
    public String insertStudio(@RequestBody Studio studio)
    {
        if(studioService.insertSelective(studio)>0)
        {
            return "succeed";
        }
        return "faild";
    }

    @ResponseBody
    @RequestMapping(value = "/updateStudio")
    public String updateStudio(@RequestBody Studio studio)
    {
        if(studioService.updateByPrimary(studio)>0)
        {
            return "succeed";
        }
        return "faild";
    }

}
