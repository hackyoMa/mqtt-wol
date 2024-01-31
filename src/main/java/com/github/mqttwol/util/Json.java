package com.github.mqttwol.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * Json
 *
 * @author hackyo
 * @since 2022/4/1
 */
public final class Json {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T toObject(ObjectNode objectNode, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(objectNode, clazz);
    }

    public static <T> T toArray(ObjectNode objectNode, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(objectNode,
                OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T getObject(ObjectNode objectNode, String key, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(objectNode.get(key), clazz);
    }

    public static <T> List<T> getArray(ObjectNode objectNode, String key, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(objectNode.get(key),
                OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json,
                    OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
