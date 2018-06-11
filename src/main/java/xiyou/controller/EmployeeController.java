package xiyou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.dao.EmployeeMapper;
import xiyou.pojo.Employee;
import xiyou.pojo.Msg;

import java.util.List;

@CrossOrigin
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeMapper employeeMapper;

//    控制跳转
    @RequestMapping("/toemployee")
    public String toemployee(){
        return "employee";
    }


    @ResponseBody
    @RequestMapping(value ="getEmployeeById",method = RequestMethod.GET)
    public Msg getEmployeeById(@RequestParam int emp_id)
    {
        return Msg.success().add("pageinfo",employeeMapper.selectByPrimaryKey(emp_id));
    }

    @ResponseBody
    @RequestMapping(value = "getAllEmployee",method = RequestMethod.GET)
    public Msg getEmployee(@RequestParam(value = "pn",defaultValue = "1")Integer page)
    {
        PageHelper.startPage(page,5);
        List<Employee> plays  = employeeMapper.selectByExample(null);
//        int p =plays.size()/10;
//        if(plays.size()%10!=0) p++;
        PageInfo pageInfo = new PageInfo(plays,5);
        return Msg.success().add("pageinfo",pageInfo);

    }

    @ResponseBody
    @RequestMapping(value = "deleteEmployeeById",method = RequestMethod.GET)
    public Msg deleteEmployeeById(@RequestParam(value = "empId") Integer emp_id)
    {
        if(employeeMapper.deleteByPrimaryKey(emp_id)>0)
        {
            return Msg.success();
        }else {
        return Msg.fail();
        }
    }

    @ResponseBody
    @RequestMapping(value = "insertEmployee",method = RequestMethod.POST)
    public Msg insertEmployee(Employee employee)
    {

//        System.out.println(employee.getEmpId() + employee.getEmpNo() + employee.getEmpName()+employee.getEmpTelNum()
//                +employee.getEmpAddr()+employee.getEmpEmail());
        if(employeeMapper.insert(employee)>0)
            return Msg.success();
        return Msg.fail();
    }

    @ResponseBody
    @RequestMapping(value = "updateEmployee",method = RequestMethod.POST)
    public Msg updateEmployee(Employee employee)

    {
        if(employeeMapper.updateByPrimaryKey(employee)>0)
            return Msg.success();
        return Msg.fail();
    }

}
