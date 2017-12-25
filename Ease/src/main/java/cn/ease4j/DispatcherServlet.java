package cn.ease4j;

import cn.ease4j.bean.Data;
import cn.ease4j.bean.Handler;
import cn.ease4j.bean.Param;
import cn.ease4j.bean.View;
import cn.ease4j.helper.BeanHelper;
import cn.ease4j.helper.ConfigHelper;
import cn.ease4j.helper.ControllerHelper;
import cn.ease4j.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig servletConfig){
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        //注册JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
        //注册处理静态资源的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toLowerCase();
        String path = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(method, path);
        if(null != handler){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<String,Object>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while(parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = Code4jUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = body.split("&");
                if(ArrayUtil.isNotEmpty(params)){
                    for (String param : params) {
                        String[] arrays = param.split("=");
                        if(ArrayUtil.isNotEmpty(arrays) && arrays.length==2){
                            String paramName = arrays[0];
                            String paramValue = arrays[1];
                            paramMap.put(paramName,paramName);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if(result instanceof View){
                View view = (View) result;
                String p = view.getPath();
                if(StringUtil.isNotEmpty(p)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+p);
                    }
                    else{
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                    }
                }
            }
            else if(result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();
                if(null != model){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.pojo2Json(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}
