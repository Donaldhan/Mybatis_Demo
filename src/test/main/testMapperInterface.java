package test.main;

import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.Dao.UserMapper;
import test.entity.User;
import test.util.JsonUtil;

public class testMapperInterface {
	private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    static{
        try{
            reader    = Resources.getResourceAsReader("mybatisConfig.xml");
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
        	u1.setAge(20);
        	u1.setName("jamel");
        	UserMapper userOperation=session.getMapper(UserMapper.class);
        	userOperation.insert(u1);
        	session.flushStatements();
	        session.commit();
	        User u2 = userOperation.selectByPrimaryKey(u1.getId());
	        System.out.println("======u1:"+JsonUtil.toJson(u2));
        	
        } 
        catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
