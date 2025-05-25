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
            field.set(instance, value);
        }
        return instance;

    }
}
