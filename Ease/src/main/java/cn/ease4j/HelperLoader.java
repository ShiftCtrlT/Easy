package cn.ease4j;

import cn.ease4j.annotation.Controller;
import cn.ease4j.helper.BeanHelper;
import cn.ease4j.helper.ClassHelper;
import cn.ease4j.helper.ControllerHelper;
import cn.ease4j.helper.IocHelper;
import cn.ease4j.util.ClassUtil;

/**
 * 助手加载器
 */
public class HelperLoader {
    /**
     * 依次加载各个助手（Helper）
     */
    public static void init(){
        Class[] classList={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class clz : classList) {
            ClassUtil.loadClass(clz.getName(),false);
        }
    }
}
