package cn.ease4j.helper;

import cn.ease4j.annotation.Controller;
import cn.ease4j.annotation.Service;
import cn.ease4j.util.ClassUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class  ClassHelper {

    private static final Set<Class<?>> CLASS_SET;
    static {
        String packageName = ConfigHelper.getAppBasePackage();
        CLASS_SET= ClassUtil.getClassSet(packageName);
    }
    /**
     * 获取应用中基础包下所有的类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用中基础包下所有的Controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clz : CLASS_SET) {
            if(clz.isAnnotationPresent(Service.class)){
                classSet.add(clz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用中基础包下所有的Service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clz : CLASS_SET) {
            if(clz.isAnnotationPresent(Controller.class)){
                classSet.add(clz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用中基础包下所有的Bean类(由框架管理的类,Controller、Service)
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        HashSet<Class<?>> beanClass = new HashSet<Class<?>>();
        beanClass.addAll(getControllerClassSet());
        beanClass.addAll(getServiceClassSet());
        return beanClass;
    }
}
