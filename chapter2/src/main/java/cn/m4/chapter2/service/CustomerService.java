package cn.m4.chapter2.service;

import cn.m4.chapter2.entity.Customer;
import cn.m4.chapter2.util.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据的服务
 */
public class CustomerService {

    //获取日志对象
    public static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);


    public static final String DRIVER;
    public static final String URL;
    public static final String USERNAME;
    public static final String PASSWORD;

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
     * 返回客户列表
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList(String keyword){
        Connection con=null;
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            String sql = "select * from customer";
            con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Customer c = new Customer();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.seteMail(rs.getString("email"));
                c.setContact(rs.getString("contact"));
                c.setRemark(rs.getString("remark"));
                c.setTelephone(rs.getString("telephone"));
                customerList.add(c);
            }
            return customerList;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 返回单个客户
     * @param id
     * @return
     */
    public Customer getCustomer(Long id){
        //TODO
        return null;
    }

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updataCustomer(long id,Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustomer(Long id){
        //TODO
        return false;
    }
}
