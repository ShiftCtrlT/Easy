package cn.m4.chapter2.helper;

import cn.m4.chapter2.util.PropUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public final class DataBaseHelper {

    //Apache Commons DBUtils中的查询执行器
    private static final QueryRunner QUERY_RUNNER  = new QueryRunner();
    //日志打印
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);


    public static final String DRIVER;
    public static final String URL;
    public static final String USERNAME;
    public static final String PASSWORD;

    //静态代码块
    //在代码块中初始化jdbc的四个变量，并获取jdbc驱动
    static{
        Properties props = PropUtil.loadProps("config.properties");
        DRIVER= props.getProperty("jdbc.driver");
        URL= props.getProperty("jdbc.url");
        USERNAME= props.getProperty("jdbc.username");
        PASSWORD= props.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            LOGGER.error("can not load one jdbc driver"+e);
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn= null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("get connection failure ",e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn){
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
//                e.printStackTrace();
                LOGGER.error("close connection failure",e);
            }
        }
    }

    /**
     * 查询实体对象列表
     * 关于返回类型的“<T> List<T>”：第一个<T>表示此处用T表示泛型，第二个List<T>才是实际的泛型使用
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(
            Class<T> entityClass,Connection conn,String sql,Object... params){
        List<T> entityList=null;
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("query entityList failure ",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection(conn);
        }
        return entityList;
    }
}
