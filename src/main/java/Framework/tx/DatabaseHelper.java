package Framework.tx;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);

    public static void beginTransaction(){
        Connection connection = DbUtils.getConnection();
        if (connection!=null){
            try {
                connection.setAutoCommit(false);
            }catch (Exception e){
                LOGGER.error("开启事务失败！！",e);
                throw new RuntimeException(e);
            }
        }
    }

    public static void commitTransaction(){
        Connection connection=DbUtils.getConnection();
        if (connection!=null){
            try {
                connection.commit();
            }catch (SQLException e){
                LOGGER.error("提交事务失败！！",e);
                throw new RuntimeException(e);
            }finally {
                DbUtils.closeConnection();
            }
        }
    }

    public static void rollbackTransaction(){
        Connection connection=DbUtils.getConnection();
        if (connection!=null){
            try {
                connection.rollback();
            }catch (SQLException e){
                LOGGER.error("事务回滚失败！！",e);
                throw new RuntimeException(e);
            }finally {
                DbUtils.closeConnection();
            }
        }
    }



}
