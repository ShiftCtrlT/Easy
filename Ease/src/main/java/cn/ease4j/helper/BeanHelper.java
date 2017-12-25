package cn.ease4j.helper;

import cn.ease4j.util.ClassUtil;
import cn.ease4j.util.ReflectionUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 定义Bean映射，用于存放Bean类与Bean实例的映射关系
 */
public class BeanHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);
    /**
     * 用于存放class--bean的键值对
     */
    public static final Map<Class<?>,Object> BEAN_MAP= new HashMap<Class<?>,Object>();

    /**
     * 静态代码块用于初始化BEAN_MAP
     * clz1 -- obj1
     * clz2 -- obj2
     */
    static{
        //获取由框架管理的bean
        Set<Class<?>> beanSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanSet) {
            Object obj = ReflectionUtil.getInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     * 获取整个Bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 根据class名称从映射中获取Bean实例
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz){
        if(!BEAN_MAP.containsKey(clz)){
            throw new RuntimeException("cannot get bean by class："+clz.getName());
        }
        return (T) BEAN_MAP.get(clz);
    }
}
