package cn.ease4j.test.proxy;

public class GreetingImpl implements  Greeting {

    @Override
    public void sayHello(String name) {
        System.out.println("Hello "+name);
    }
}
