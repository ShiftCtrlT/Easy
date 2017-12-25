package cn.ease4j.test.proxy;

/**
 * 静态代理示例
 *
 * 技术要点：
 *  1.实现需要代理的接口;
 *  2.在重写的方法中调用接口实现类的方法，以及代理类自己的私有方法（增强）
 *
 * 缺点：每一个被代理对象都需要一个代理类，每个代理类只能代理一个类，长期以往系统中会有多个代理类
 */
public class StaticProxy implements  Greeting{
    private GreetingImpl greetingImpl;

    public StaticProxy(GreetingImpl greetingImpl) {
        this.greetingImpl = greetingImpl;
    }

    @Override
    public void sayHello(String name) {
        before();
        greetingImpl.sayHello(name);
        after();
    }

    private void before(){
        System.out.println("Before");
    }

    private void after(){
        System.out.println("After");
    }
}
