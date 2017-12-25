package cn.ease4j.bean;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图对象
 */
public class View {
    //视图路径
    private String path;
    //模型数据
    private Map<String ,Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<String,Object>();
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View addModel(String key, Object value){
        model.put(key,value);
        return this;
    }
}
