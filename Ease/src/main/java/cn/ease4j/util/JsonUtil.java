package cn.ease4j.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JSON 工具类
 */
public class JsonUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String pojo2Json(T obj) throws JsonProcessingException {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            LOGGER.error("convert POJO to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T json2Pojo(String json,Class<T> clz){
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(json,clz);
        } catch (IOException e) {
//            e.printStackTrace();
            LOGGER.error("convert json to pojo failure ",e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
