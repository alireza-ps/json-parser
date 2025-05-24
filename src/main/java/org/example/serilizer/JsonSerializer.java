package org.example.serilizer;

import org.example.annotation.RenameProperty;
import org.example.annotation.SkipProperty;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class JsonSerializer {

    private static final Set<Class<?>> WRAPPER_TYPES = Set.of(Byte.class, Short.class,
            Integer.class, Float.class, Double.class, Long.class, Boolean.class, Character.class);

    public static String toJson(Object object) {
        if (object == null) return "null";

        if (isPrimitiveOrWrapperOrString(object.getClass())) return serializePrimitiveToString(object);

        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        StringBuilder json = new StringBuilder();
        serialize(object, json, fields);
        return json.toString();
    }

    private static void serialize(Object object, StringBuilder json, Field[] fields) {
        json.append("{");
        formatFields(object, fields, json);
        json.append("}");
    }

    private static void formatFields(Object object, Field[] fields, StringBuilder json) {
        Field[] filteredFields = Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(SkipProperty.class))
                .toArray(Field[]::new);
        for (int i = 0; i < filteredFields.length; i++) {
            Field field = filteredFields[i];
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            serializeObject(json, field, value);
            if (i != filteredFields.length - 1) {
                json.append(", ");
            }
        }
    }


    private static void serializeObject(StringBuilder json, Field field, Object value) {
        String keyName = getFieldName(field);

        json.append("\"").append(keyName).append("\"").append(" : ");

        if (value == null) {
            json.append("null");
        } else if (isPrimitiveOrWrapperOrString(value.getClass())) {
            json.append(serializePrimitiveToString(value));
        } else if (isEnum(value)) {
            json.append(serializeEnum(value));
        } else if (isListOrArray(value)) {
            json.append(serializeArrayOrList(value));
        } else if (value instanceof Map<?, ?> map) {
            json.append(serializeMap(map));
        } else {
            // Else it's a complex object; serialize it recursively
            // Recursive call: assumes toJson(Object) builds JSON for this object.
            json.append(toJson(value));
        }
    }

    private static String serializeEnum(Object value) {
        return "\"" + value + "\"";
    }

    private static boolean isEnum(Object value) {
        return value != null && value.getClass().isEnum();
    }

    private static String getFieldName(Field field) {
        return field.isAnnotationPresent(RenameProperty.class)
                ? field.getAnnotation(RenameProperty.class).name()
                : field.getName();
    }

    private static String serializeMap(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        Set<? extends Map.Entry<?, ?>> entries = map.entrySet();
        Iterator<? extends Map.Entry<?, ?>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> next = iterator.next();
            json.append("\"").append(next.getKey()).append("\"").append(" : ");
            json.append(toJson(next.getValue()));
            if (iterator.hasNext()) json.append(", ");
        }
        json.append("}");
        return json.toString();
    }

    private static boolean isListOrArray(Object value) {
        Class<?> type = value.getClass();
        return type.isArray() || value instanceof List<?>;
    }

    private static String serializeArrayOrList(Object value) {
        StringBuilder json = new StringBuilder();
        if (value.getClass().isArray()) {
            json.append("[");
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                Object element = Array.get(value, i);
                json.append(toJson(element));
                if (i < length - 1) json.append(", ");
            }
            json.append("]");
        } else if (value instanceof List<?> elements) {
            json.append("[");
            for (int i = 0; i < elements.size(); i++) {
                json.append(toJson(elements.get(i)));
                if (i < elements.size() - 1) json.append(", ");
            }
            json.append("]");
        }
        return json.toString();

    }

    private static String serializePrimitiveToString(Object value) {
        if (value instanceof String s) {
            return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        }
        return value.toString();
    }

    private static boolean isPrimitiveOrWrapperOrString(Class<?> type) {
        return type.isPrimitive() || WRAPPER_TYPES.contains(type) || type == String.class;
    }
}