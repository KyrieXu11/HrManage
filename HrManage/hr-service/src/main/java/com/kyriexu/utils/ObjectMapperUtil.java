package com.kyriexu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author KyrieXu
 * @since 2020/3/31 16:06
 **/
public class ObjectMapperUtil {
    /**
     * 对象 -> string
     *
     * @param obj 对象
     * @return 字符串
     * @throws JsonProcessingException json转化失败
     */
    public static <T> String BeanToString(T obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    /**
     * string -> 对象
     *
     * @param str       string
     * @param reference 转换的对象类型(jackson包下的)
     * @param <T>       对象的类型
     * @return 对象
     * @throws JsonProcessingException json转化失败
     */
    public static  <T> T  StringToBean(String str, TypeReference<T> reference) throws JsonProcessingException {
        return new ObjectMapper().readValue(str, reference);
    }
}
