package cn.ease4j.bean;

/**
 * 封装请求信息
 */
public class Request {
    //请求方法(GET/POST)
    private String requestMethod;
    //请求路径(../../../..)
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

}
