package cn.ease4j.bean;

import java.lang.reflect.Method;

/**
 * Controller类
 */
public class Handler {
    //实际执行方法所在的controller类
    //或者说当前@Action标记的方法所在的controller类
    private Class<?> controllerClass;
    //实际执行的方法
    //或者说当前@Action标记的方法
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
