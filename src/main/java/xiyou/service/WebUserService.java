package xiyou.service;

import xiyou.pojo.User;

import java.util.List;

public interface WebUserService {

    /**
     * 通过emp_no查询user
     * @param emp_no
     * @return
     */
    User getUserByNo(String emp_no);

    /**
     * 返回所有user
     * @return
     */
    List<User> getUser();

    /**
     * 获取没有在user中注册的employee
     * @return
     */
    List<String> UserNotInEmployee();
}
