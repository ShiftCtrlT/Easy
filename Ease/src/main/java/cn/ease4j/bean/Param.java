package cn.ease4j.bean;

import cn.ease4j.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象（内含 参数名--参数值 映射）
 */
public class Param {
    //参数名--参数值 映射
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public long getLong(String paraName){
        return CastUtil.castLong(paramMap.get(paraName));
    }
}
