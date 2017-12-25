package cn.ease4j.util;

import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil {

    public static Boolean isNotEmpty(Object[] array){
        return ArrayUtils.isNotEmpty(array);
    }

    public static Boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
