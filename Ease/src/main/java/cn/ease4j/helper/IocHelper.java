package cn.ease4j.helper;

import cn.ease4j.annotation.Inject;
import cn.ease4j.util.ArrayUtil;
import cn.ease4j.util.CollectionUtil;
import cn.ease4j.util.ReflectionUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public class IocHelper {

    static {
        //获取整个Bean映射
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            //Map遍历
            for (Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()) {
                Class<?> beanClass = beanEntity.getKey();
                Object beanInstance = beanEntity.getValue();
                //获取class对象的所有域（属性）
                Field[] declaredFields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(declaredFields)){
                    for (Field field : declaredFields) {
                        //匹配@Inject注解
                        if(field.isAnnotationPresent(Inject.class)){
                            //获取域的class对象
                            Class<?> type = field.getType();
                            //生成域的实例
                            Object fieldInstance = ReflectionUtil.getInstance(type);
                            if(null != fieldInstance){
                                //将域实例设置进bean实例
                                ReflectionUtil.setField(beanInstance,field,fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
