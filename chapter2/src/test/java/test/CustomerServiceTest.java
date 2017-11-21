package test;

import cn.m4.chapter2.entity.Customer;
import cn.m4.chapter2.helper.DataBaseHelper;
import cn.m4.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.activation.DataHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService=new CustomerService();
    }

    @Before
    public void init() throws IOException {
        DataBaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCusotmerListTest(){
        List<Customer> customerList = customerService.getCustomerList("");
        System.out.println(customerList.size());
//        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void getCustomerTest(){
        Long id = 1l;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest(){
        Map<String,Object> fieldMap = new HashMap<String,Object>();
        fieldMap.put("name","cust001");
        fieldMap.put("contact","M4");
        fieldMap.put("telephone","18140559029");
        boolean result=customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updataCustomerTest(){
        Long id = 1l;
        Map<String,Object> fieldMap = new HashMap<String,Object>();
        fieldMap.put("name","cust002");
        customerService.updataCustomer(id,fieldMap);
    }

    @Test
    public void deleteCustomerTest(){
        Long id=4l;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }











}
