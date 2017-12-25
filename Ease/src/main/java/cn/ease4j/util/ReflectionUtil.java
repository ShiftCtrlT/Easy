package cn.ease4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param clz
     * @return
     */
    public static <T> T getInstance(Class<T> clz){
        T instance= null;
        try {
            instance= clz.newInstance();
        } catch (Exception e) {
            LOGGER.error("get instance error",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param params
     * @return
     */
    public static Object invokeMethod(Object obj, Method method,Object... params){
        Object result ;
        try {
            //方法设置为可以被反射机制调用
            method.setAccessible(true);
            result=method.invoke(obj,params);
        } catch (Exception e) {
            LOGGER.error("method invoke failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * （将在IOCHelper中使用）
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field,Object value){
        //todo
        try {
            //方法设置为可以被反射机制调用
            field.setAccessible(true);
            //为obj这个对象中的field的值设置为value
            field.set(obj,value);
        } catch (Exception e) {
            LOGGER.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }

}
