package xiyou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.UserMapper;
import xiyou.pojo.Msg;
import xiyou.pojo.User;
import xiyou.service.impl.WebUserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin

public class UserController{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WebUserServiceImpl userService;

    @RequestMapping("/touser")
    public String touser(){
        return "user";
    }


    @ResponseBody
    @RequestMapping(value = "login")
    public User login(@RequestParam String name, @RequestParam String password, HttpServletRequest request, HttpServletResponse response)
    {
        User user = userMapper.selectByPrimaryKey(name);

        if(password.equals(user.getEmpPass()))
        {
            return user;
        }
        return user;
    }

    @ResponseBody
    @RequestMapping(value ="getUserById",method = RequestMethod.GET)
    public Msg getUserById(@RequestParam String emp_no)
    {
        User user = userMapper.selectByPrimaryKey(emp_no);
        return Msg.success().add("pageinfo",user);
    }

    @ResponseBody
    @RequestMapping(value = "getAllUser",method = RequestMethod.GET)
    public Msg getAllUser(@RequestParam(value = "pn",defaultValue = "1")Integer page){
        PageHelper.startPage(page,5);
        List<User> users  = userMapper.selectByExample(null);
//        int p =users.size()/10;
//        if (users.size()%10!=0) p++;
        PageInfo pageInfo = new PageInfo(users,5);
        return Msg.success().add("pageinfo",pageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserNotInEmployee",method = RequestMethod.GET)
    public Msg getUserNotInEmployee(){
        List<String> user = userService.UserNotInEmployee();
        return Msg.success().add("info",user);
    }

    @ResponseBody
    @RequestMapping(value = "deleteUserById",method = RequestMethod.GET)
    public Msg deleteUserById(@RequestParam String emp_no)
    {
        if(userMapper.deleteByPrimaryKey(emp_no)>0)
        {
            return Msg.success();
        }
        return Msg.fail();
    }

    @ResponseBody
    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    public Msg insertUser( User user)
    {
        System.out.println(user.getEmpNo()+user.getEmpPass()+user.getType()+user.getHeadPath());
        if(userMapper.insertSelective(user)>0)
            return Msg.success();
        return Msg.fail();
    }

    @ResponseBody
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public Msg updatetUser( User user)
    {
//        System.out.println(user.getEmpNo()+"\n"+user.getEmpPass()+"\n"+user.getType()+"\n"+user.getHeadPath());
        if(userMapper.updateByPrimaryKeySelective(user)>0)
            return Msg.success();
        return Msg.fail();
    }



}
