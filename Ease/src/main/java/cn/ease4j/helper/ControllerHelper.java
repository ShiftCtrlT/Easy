package cn.ease4j.helper;

import cn.ease4j.annotation.Action;
import cn.ease4j.bean.Handler;
import cn.ease4j.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {
    //请求--处理器 映射
    public static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static{
        //获取所有的Controller Bean
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if(null != controllerSet && controllerSet.size()>0){
            for (Class<?> c : controllerSet) {
                //获取class对象中的所有方法
                Method[] methods = c.getDeclaredMethods();
                if(null != methods && methods.length>0){
                    for (Method m : methods) {
                        //匹配@Action注解
                        if(m.isAnnotationPresent(Action.class)){
                            Action action = m.getAnnotation(Action.class);
                            String mapping = action.value();
                            //正则匹配
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] arrays = mapping.split(":");
                                String requestMethod = arrays[0];
                                String requestPath = arrays[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(c, m);
                                //封装到 请求--处理器的映射
                                ACTION_MAP.put(request,handler);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据请求方法（GET、POST），请求路径（../../..），
     * 获取某个Hander(包括controller class以及对应的method)
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        return ACTION_MAP.get(new Request(requestMethod,requestPath));
    }
}
