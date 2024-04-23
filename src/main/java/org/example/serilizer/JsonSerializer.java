package org.example.serilizer;

import java.lang.reflect.Field;

public class JsonSerializer {
    public static String toJson(Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder json = new StringBuilder();
        json.append("{");
        buildJsonFields(object, declaredFields, json);
        json.append("}");
        return json.toString();
    }

    private static void buildJsonFields(Object object, Field[] declaredFields, StringBuilder json) {
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null && !field.getType().isPrimitive() && !field.getType().getName().startsWith("java.lang")
                        && !field.getType().getName().startsWith("java.time")) {
                    Class<?> childClass = value.getClass();
                    Field[] childObjectFields = childClass.getDeclaredFields();
                    json.append("\"");
                    json.append(field.getName())
                            .append("\"")
                            .append(" : ");
                    json.append("{");
                    buildJsonFields(value, childObjectFields, json);
                    json.append("}");
                    if (i != declaredFields.length - 1) {
                        json.append(", ");
                    }
                } else {
                    appendDoubleQuote(json, field, value);
                    if (i != declaredFields.length - 1)
                        json.append(", ");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void appendDoubleQuote(StringBuilder json, Field field, Object value) {
        json.append("\"")
                .append(field.getName())
                .append("\"")
                .append(" : ")
                .append("\"")
                .append(value)
                .append("\"");

    }
}
