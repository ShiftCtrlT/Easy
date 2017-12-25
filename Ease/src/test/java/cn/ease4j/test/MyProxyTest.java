package cn.ease4j.test;

import cn.ease4j.test.proxy.*;
import org.junit.Test;

public class MyProxyTest {
    @Test
    public void staticProxtTest(){
        StaticProxy sp = new StaticProxy(new GreetingImpl());
        sp.sayHello("static");
    }

    @Test
    public void jdkDynamicProxyTest(){
        Greeting greeting = new JdkDynamicProxy(new GreetingImpl()).getInstance();
        greeting.sayHello("dynamic");
    }

    @Test
    public void cglibDymanicProxyTest(){
        CgLibDynamicProxy instance = CgLibDynamicProxy.getInstance();
        GreetingImpl greeting = instance.getProxy(GreetingImpl.class);
        greeting.sayHello("cglib");
    }
}
