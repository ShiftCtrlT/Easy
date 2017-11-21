package cn.m4.chapter2.helper;

import cn.m4.chapter2.util.CollectionUtil;
import cn.m4.chapter2.util.PropUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public final class DataBaseHelper {

    //Apache Commons DBUtils中的查询执行器
    private static final QueryRunner QUERY_RUNNER  = new QueryRunner();
    //日志打印
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    // 使用ThreadLocal存放本地线程变量
    // 将ThreadLocal理解为一个隔离线程的容器
    private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();


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
        Connection conn= CONNECTION_HOLDER.get();
        if(conn == null){
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
//            e.printStackTrace();
                LOGGER.error("get connection failure ",e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
//                e.printStackTrace();
                LOGGER.error("close connection failure",e);
                CONNECTION_HOLDER.remove();
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
            Class<T> entityClass,String sql,Object... params){
        List<T> entityList=null;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("query entityList failure ",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询单个实体
     * @param entityClass
     * @param sql
     * @param param
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object...param){
        T entity;
        Connection conn = getConnection();
        try {
            entity=QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),param);
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("query entity failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询的SQL语句
     * @param sql
     * @param param
     * @param <T>
     * @return
     */
    public static <T> List<Map<String,Object>> executeQuery(String sql,Object...param){

        List<Map<String,Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), param);
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("execute dql failure "+e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 执行更新语句，包括update，insert，delete
     * 返回具体受影响的行数，据此可以提供更加具体的update、insert、delete方法
     * @param sql
     * @param param
     * @return
     */
    public static int executeUpdate(String sql,Object... param){
        //受影响的行数
        int rows=0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, param);

        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("execute update failure"+e);
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static<T> Boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("fieldMap is empty");
            return false;
        }
        //insert into table ("","","") values (xx,xx,xx);
        String sql="INSERT INTO "+getTableName(entityClass);
        StringBuilder columns=new StringBuilder("(");
        StringBuilder values=new StringBuilder("(");
        for (String key : fieldMap.keySet()) {
            columns.append(key).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "),columns.length(),") ");
        values.replace(values.lastIndexOf(", "),values.length(),") ");
        sql+=columns + "VALUES" + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params)==1;
    }

    /**
     * 更新
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> Boolean updateEntity(Class<T> entityClass,Long id,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("fieldMap is empty");
            return false;
        }
        //UPDATE table SET ""=xx,""=xx WHRER ""=xx
        String sql="UPDATE "+getTableName(entityClass)+" SET ";
        StringBuilder columns=new StringBuilder("");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?,");
        }
        sql+=columns.substring(0,columns.lastIndexOf(","))+" WHERE id=?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return  executeUpdate(sql, params)==1;
    }

    /**
     * 删除
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> Boolean deleteEntity(Class<T> entityClass,Long id){
        String sql="DELETE FROM "+getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql,id)==1;

    }




    /**
     * 获取表名
     * 与实体类名一致，实际是获取类名
     * @param entityClass
     * @param <T>
     * @return
     */
    private static <T> String getTableName(Class<T> entityClass){
        return entityClass.getSimpleName();
    }
}
