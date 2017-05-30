package test.main;

import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.entity.User;
import test.util.JsonUtil;

public class testSqlSession {
	private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    static{
        try{
            reader    = Resources.getResourceAsReader("mybatisConfig.xml");
        /*    System.out.println("========path:"+Thread.currentThread().getContextClassLoader().getResource("mybatisConfig.xml"));
            Thread.currentThread().getContextClassLoader();
			System.out.println("========systemPath:"+ClassLoader.getSystemResource("mybatisConfig.xml"));*/
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
    
    public static void main(String[] args) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	User u1 = new User();
        	u1.setAge(12);
        	u1.setName("donald");
	        session.insert("test.Dao.UserMapper.insert", u1);
	        User u2 = session.selectOne("test.Dao.UserMapper.selectByPrimaryKey", u1.getId());
	        System.out.println("======u1:"+JsonUtil.toJson(u2));
	        User u3 = new User();
	        u3.setAge(30);
	        u3.setName("jamel");
	        session.insert("test.Dao.UserMapper.insert", u3);
	        User u4 = session.selectOne("test.Dao.UserMapper.selectByPrimaryKey", u3.getId());
	        System.out.println("======u3:"+JsonUtil.toJson(u4));
//	        session.flushStatements();
	        session.commit();
	        u3.setName("rain");
	        session.update("test.Dao.UserMapper.updateByPrimaryKeySelective", u3);
//	        session.commit();
	        User u5 = session.selectOne("test.Dao.UserMapper.selectByPrimaryKey", u3.getId());
	        System.out.println("======cache-u3-name:"+u5.getName());
        } 
        catch(Exception e){
        	e.printStackTrace();
        }
        finally {
        	session.close();
        }
    }
}
