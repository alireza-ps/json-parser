package com.github.alirezaps.jsonserializer.deserializer;

import com.github.alirezaps.jsonserializer.parser.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class JsonDeserializer {
    public <T> T fromJson(String json, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = tClass.getDeclaredConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        Map<String, Object> map = new JsonParser(json).parse();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = map.get(field.getName());
            Class<?> targetType = field.getType();
            Object obj = convertType(value, targetType);
            field.set(instance, obj);
        }
        return instance;

    }

    private Object convertType(Object value, Class<?> targetType) {
        if (value == null) return null;
        if (targetType == byte.class || targetType == Byte.class)
            return ((Number) value).byteValue();
        else if (targetType == short.class || targetType == Short.class)
            return ((Number) value).shortValue();
        else if (targetType == int.class || targetType == Integer.class)
            return ((Number) value).intValue();
        else if (targetType == long.class || targetType == Long.class)
            return ((Number) value).longValue();
        else if (targetType == double.class || targetType == Double.class)
            return ((Number) value).doubleValue();
        else if (targetType == float.class || targetType == Float.class)
            return ((Number) value).floatValue();
        else if (targetType == boolean.class || targetType == Boolean.class)
            return value instanceof Boolean ? value : Boolean.parseBoolean(value.toString());
        else if (targetType == String.class)
            return value.toString();
        else if (targetType == Enum.class)
            return Enum.valueOf((Class<Enum>) targetType, value.toString());
        throw new RuntimeException("Unsupported targetType.");
    }
}
