package cn.m4.chapter2.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * 注意其中【泛型】的处理
 */
public class CollectionUtil {

    /**
     * 判断Collection是否为空
     * @param collection
     * @return
     */
    public static Boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Collection是否不为空
     * @param collection
     * @return
     */
    public static Boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);

    }

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static Boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断Map是否不为空
     * @param map
     * @return
     */
    public static Boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}

