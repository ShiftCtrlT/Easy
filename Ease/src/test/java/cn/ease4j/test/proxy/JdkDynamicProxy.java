package cn.ease4j.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理示意
 * 技术要点：
 *  1.实现InvocationHandler接口
 *  2.重写invoke方法，重点是其中的method.invoke(Object,Object[]);
 *  3.新增公有的getInstance方法，方法体中用的是Proxy.newProxyInstance方法
 *
 */
public class JdkDynamicProxy implements InvocationHandler{
    private Object target;

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getInstance(){
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }

}
