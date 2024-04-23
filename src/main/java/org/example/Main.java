package org.example;

import org.example.serilizer.JsonSerializer;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        User user = new User(1L, "jon", true, new Role(1L, "ADMIN"),
                new Product(100L,Instant.now(),"Digital", UUID.randomUUID().toString()));
        var json = JsonSerializer.toJson(user);
        System.out.println(json);
    }


    public static String toJson(Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder json = getStringBuilder(object, declaredFields);
        System.out.println(json);
        return json.toString();
    }

    private static StringBuilder getStringBuilder(Object object, Field[] declaredFields) {
        if (object == null) {
            return null;
        }
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            try {
                if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.lang")) {
                    Class<?> subClass = field.getDeclaringClass();
                    Field[] fields = subClass.getFields();
                }
                json.append("\t");
                json.append(field.getName() + " : " + field.get(object));
                if (i != declaredFields.length - 1)
                    json.append(",\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        json.append("\n}");
        return json;
    }
}