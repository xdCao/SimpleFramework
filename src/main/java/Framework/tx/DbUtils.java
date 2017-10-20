package Framework.tx;
/*
    created by xdCao on 2017/10/19
*/

import Framework.helpers.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {

    private static final Logger LOGGER= LoggerFactory.getLogger(DbUtils.class);

    private static ThreadLocal<Connection> connectionContainer=new ThreadLocal<Connection>();

    public static Connection getConnection(){
        Connection connection=connectionContainer.get();
        try {
            if (connection==null){
                Class.forName(ConfigHelper.getJdbcDriver());
                connection= DriverManager.getConnection(ConfigHelper.getJdbcUrl(),ConfigHelper.getJdbcUserName(),ConfigHelper.getJdbcPassword());
            }
        }catch (Exception e){
            LOGGER.error("获取数据库连接失败！！",e);
            e.printStackTrace();
        }finally {
            connectionContainer.set(connection);
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection=connectionContainer.get();
        try {
            if (connection!=null){
                connection.close();
            }
        }catch (Exception e){
            LOGGER.error("关闭数据库连接失败！！",e);
            e.printStackTrace();
        }finally {
            connectionContainer.remove();
        }
    }


}
