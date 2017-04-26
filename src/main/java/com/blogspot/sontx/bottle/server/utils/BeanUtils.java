package com.blogspot.sontx.bottle.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.Map;

public final class BeanUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(@NonNull Object bean) {
        return objectMapper.convertValue(bean, Map.class);
    }

    private BeanUtils() {
    }
}
