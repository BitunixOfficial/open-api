package com.bitunix.openapi.utils;

import com.bitunix.openapi.constants.CommonResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kotlin.jvm.internal.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJsonString(Object object){
        if (object == null){
            return null;
        }
        if (object instanceof String){
            return (String) object;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readToObject(String json, Class<T> cls)  {
        if (Objects.isNull(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> readToList(String json, Class<T> cls)  {
        if (Objects.isNull(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(ArrayList.class,cls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> CommonResult<T> readToCommonResult(String json, Class<T> cls)  {
        if (Objects.isNull(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(CommonResult.class,cls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> CommonResult<ArrayList<T>> readToCommonResultList(String json, Class<T> cls)  {
        if (Objects.isNull(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(CommonResult.class,objectMapper.getTypeFactory().constructParametricType(ArrayList.class,cls)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
