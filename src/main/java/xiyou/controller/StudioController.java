package xiyou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.StudioMapper;
import xiyou.pojo.Msg;
import xiyou.pojo.Studio;
import xiyou.service.WebStudioService;

import java.util.List;
@CrossOrigin
@Controller
public class StudioController {
    @Autowired
    private StudioMapper studioMapper;
    @Autowired
    private WebStudioService webStudioService;

    @RequestMapping("/tostudio")
    public String tostudio(){
        return "studio";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllStudio",method = RequestMethod.GET)
    public Msg getAllStudio(@RequestParam(value  = "pn",defaultValue = "1")Integer page)
    {
        PageHelper.startPage(page,5);
        List<Studio> studios = studioMapper.selectByExample(null);
//        int p = studios.size()/10;
//        if(studios.size()%10!=0) p++;
        PageInfo pageInfo = new PageInfo(studios,5);
//        return pageInfo;
        return Msg.success().add("pageinfo",pageInfo);
    }
    @ResponseBody
    @RequestMapping(value = "/getStudioById",method = RequestMethod.GET)
    public Msg getStudioById(@RequestParam(value = "studioId") Integer studio_id)
    {
//        return studioService.selectByPrimaryKey(studio_id);
        Studio studio = webStudioService.selectByPrimaryKey(studio_id);
        return Msg.success().add("info",studio);
    }

    @ResponseBody
    @RequestMapping(value = "/updateStudioById",method = RequestMethod.POST)
    public Msg updateStudioById(Studio studio)
    {
        if(studioMapper.updateByPrimaryKey(studio)>0){
            return Msg.success();
        }
        return Msg.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/deleteStudioById",method = RequestMethod.GET)
    public Msg deleteStudioById(@RequestParam(value = "studioId")Integer studio_id)
    {
        if(studioMapper.deleteByPrimaryKey(studio_id)>0)
        {
            return Msg.success();
        }
        return Msg.fail();
    }

}
