package xiyou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xiyou.dao.PlayMapper;
import xiyou.pojo.Msg;
import xiyou.pojo.Play;
import xiyou.service.PlayService;
import xiyou.service.WebPlayService;
import xiyou.service.impl.WebPlayServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
@CrossOrigin
@Controller

public class PlayController {

    @Autowired
    private PlayMapper playMapper;

    @Autowired
    private WebPlayServiceImpl playService;

    @RequestMapping("/toplay")
    public String toplay(){
        return "play";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllPlay",method = RequestMethod.GET)
    public Msg getAllPlay(@RequestParam(value = "pn",defaultValue = "1")Integer page){
        PageHelper.startPage(page,8);
//        List<Play> plays  = playMapper.selectByExample(null);
        List<Play> plays = playService.selectAllPlay();
        PageInfo pageInfo = new PageInfo(plays,5);
       return Msg.success().add("pageinfo",pageInfo);
    }

    @RequestMapping(value = "getPlayById",method = RequestMethod.GET)
    public Msg getPlayById(@RequestParam int  playId)
    {
        return Msg.success().add("info",playService.selectByPrimaryKey(playId));
    }

    @ResponseBody
    @RequestMapping(value = "deletePlayById",method = RequestMethod.GET)
    public Msg deletePlayById(@RequestParam(value = "playId") int play_id)
    {
        if(playMapper.deleteByPrimaryKey(play_id)>0)
        {
            return Msg.success();
        }
        return Msg.fail();
    }

//    @ResponseBody
//    @RequestMapping(value = "insertPlay",method = RequestMethod.POST)
//    public Msg insertPlay(Play play)
//    {
//        if(playMapper.insert(play)>0)
//            return Msg.success();
//        return Msg.fail();
//    }

//    @ResponseBody
    @RequestMapping(value = "insertPlay",method = RequestMethod.POST)
    public String insertPlay(@RequestParam(value = "playId")Integer playId,
                          @RequestParam(value = "playName")String playName,
                          @RequestParam(value = "playLang")String playLang,
                          @RequestParam(value = "playIntroduction")String playIntroduction,
                          @RequestParam(value = "playType")String playType,
                          @RequestParam(value = "playLength")Integer playLength,
                          @RequestParam(value = "playTicketPrice")BigDecimal playTicketPrice,
                          @RequestParam(value = "playStatus")Short playStatus,
                          @RequestParam(value = "playImage")MultipartFile playImage,
                             HttpServletRequest request)
    throws IOException{

        System.out.println(playId+playName);

        Play play = new Play();
        play.setPlayId(playId);
        play.setPlayName(playName);
        play.setPlayLang(playLang);
        play.setPlayIntroduction(playIntroduction);
        play.setPlayType(playType);
        play.setPlayLength(playLength);
        play.setPlayTicketPrice(playTicketPrice);
        play.setPlayStatus(playStatus);

        if(playImage!=null) {
            String fileAllName = playImage.getOriginalFilename();
            String filenameplayId = "play" + playName;
            String fileLastName = fileAllName.substring(fileAllName.lastIndexOf("."));
            String fileRealName = filenameplayId.concat(fileLastName);
            String beforePath = request.getServletContext().getRealPath("/");
            String realbeforePath = beforePath.substring(0,beforePath.indexOf("target"));
            String path = "src/main/webapp/images/";
            String realPath = realbeforePath+ path;
            play.setPlayImage("/images/" + fileRealName);
            File file = new File(realPath,fileRealName);
            if(!file.exists()){
                file.mkdirs();
            }
                playImage.transferTo(file);
        }else {
            String path404 = "/images/404.jpg";
//            File file = new File(path404);
            play.setPlayImage(path404);
        }
        playMapper.insertSelective(play);

        return "success";

//        return Msg.success();
    }


//    @ResponseBody
//    @RequestMapping(value = "updatePlay",method = RequestMethod.POST)
//    public Msg updatePlay(Play play)
//    {
//        Play playgetimages = playMapper.selectByPrimaryKey(play.getPlayId());
//
//
//        if(play.getPlayImage()==""||play.getPlayImage()==null){
//            play.setPlayImage(playgetimages.getPlayImage());
//        }
//
//        if(playMapper.updateByPrimaryKeySelective(play)>0)
//            return Msg.success();
//        return Msg.fail();
//    }

    @RequestMapping(value = "/updatePlay",method = RequestMethod.POST)
    public String updatePlay(
            @RequestParam(value = "playId")Integer playId,
            @RequestParam(value = "playName")String playName,
            @RequestParam(value = "playLang")String playLang,
            @RequestParam(value = "playIntroduction")String playIntroduction,
            @RequestParam(value = "playType")String playType,
            @RequestParam(value = "playLength")Integer playLength,
            @RequestParam(value = "playTicketPrice")BigDecimal playTicketPrice,
            @RequestParam(value = "playStatus")Short playStatus,
            @RequestParam(value = "playImage")MultipartFile playImage,
            HttpServletRequest request
    )throws IOException{
        Play play = new Play();
        play.setPlayId(playId);
        play.setPlayName(playName);
        play.setPlayLang(playLang);
        play.setPlayIntroduction(playIntroduction);
        play.setPlayType(playType);
        play.setPlayLength(playLength);
        play.setPlayTicketPrice(playTicketPrice);
        play.setPlayStatus(playStatus);

        if(playImage!=null) {
            String fileAllName = playImage.getOriginalFilename();

            String filenameplayId = "play" + playName;
            String fileLastName = fileAllName.substring(fileAllName.lastIndexOf("."));
            String fileRealName = filenameplayId.concat(fileLastName);
            String beforePath = request.getServletContext().getRealPath("/");
            String realbeforePath = beforePath.substring(0,beforePath.indexOf("target"));
            String path = "src/main/webapp/images/";
            String realPath = realbeforePath+ path;
            play.setPlayImage("/images/" + fileRealName);
            File file = new File(realPath,fileRealName);

            playImage.transferTo(file);
        }else {
            String path404 = "/images/404.jpg";
//            File file = new File(path404);
            play.setPlayImage(path404);
        }
        playMapper.updateByPrimaryKeySelective(play);

        return "play";

    }




}
