package util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Deng Jialong 2017/02/17
 */
public enum JacksonMapper {
    INSTANCE;
    private static final ObjectMapper mapper = new ObjectMapper().configure( JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true );

    public static Map<String, ?> readJsonToObject(String jsonData) {
        Map map = new HashMap();
        try {
            map = mapper.readValue(jsonData, Map.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static String writeObjectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return "{}";
    }


}
