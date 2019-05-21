import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import xiyou.dao.UserMapper;
import xiyou.pojo.User;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class Test {
    @org.junit.Test
    public void Print()
    {
        System.out.println("你好");
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

            SqlSession sqlSession = sessionFactory.openSession();
            User user =sqlSession.selectOne("xiyou.dao.UserMapper.selectByPrimaryKey","000001");

            System.out.println(user.getEmpNo());
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user1 = userMapper.selectByPrimaryKey("000001");
            System.out.println(user1.getEmpPass());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
