package cn.ease4j.bean;

/**
 * 返回的数据对象（将在Servlet中转为Json）
 */
public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
