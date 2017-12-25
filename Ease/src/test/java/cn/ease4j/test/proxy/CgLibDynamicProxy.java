package cn.ease4j.test.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于cglib的动态代理
 */
public class CgLibDynamicProxy implements MethodInterceptor{

    //单例模式
    private static CgLibDynamicProxy proxy = new CgLibDynamicProxy();

    private CgLibDynamicProxy() {
        this.proxy = proxy;
    }

    public static CgLibDynamicProxy getInstance(){
        return proxy;
    }


    /**
     *
     * @param clz   被代理对象的class
     * @param <T>
     * @return
     */
    public <T> T getProxy(Class<T> clz){
        //Enhancer.create(Class type,CallBack callback)
        return (T) Enhancer.create(clz,this);
    }

    /**
     * @param target    被代理对象
     * @param method    被代理（拦截）的方法
     * @param args      被代理方法的参数
     * @param proxy     代理对象（方法拦截器）
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        //通常，相对易于理解的写法可能是method.invoke
        //这里理解为由proxy（方法拦截器、方法代理器）代为执行，就写成了proxy.invoke
        Object result = proxy.invokeSuper(target, args);
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
