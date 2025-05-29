package com.github.alirezaps.jsonserializer.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

    //@formatter:off
    enum Color{RED,BLUE}
    record PrimitiveRecord(long id, String name) {}

    record EnumRecord(Color color) {}

    record ListRecord(List<Double> list) {}

    record MapRecord(Map<Integer,String> map) {}

    record NestedRecord(long id,PrimitiveRecord record){}
    //@formatter:on


    @Test
    void test_convert_to_json_with_primitive_object() {
        PrimitiveRecord record = new PrimitiveRecord(1L, "test");
        String json = JsonSerializer.toJson(record);
        String expected = "{\"id\" : 1, \"name\" : \"test\"}";
        Assertions.assertEquals(expected, json);
    }

    @Test
    void test_convert_to_json_with_enum_object() {
        EnumRecord record = new EnumRecord(Color.BLUE);
        String json = JsonSerializer.toJson(record);
        String expected = "{\"color\" : \"BLUE\"}";
        Assertions.assertEquals(expected, json);
    }


    @Test
    void test_convert_to_json_with_list_object() {
        ListRecord record = new ListRecord(List.of(1.1, 1.2, 1.3));
        String json = JsonSerializer.toJson(record);
        String expected = "{\"list\" : [1.1, 1.2, 1.3]}";
        Assertions.assertEquals(expected, json);
    }


    @Test
    void test_convert_to_json_with_map_object() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "test1");
        map.put(2, "test2");
        MapRecord record = new MapRecord(map);
        String json = JsonSerializer.toJson(record);
        String expected = "{\"map\" : {\"1\" : \"test1\", \"2\" : \"test2\"}}";
        Assertions.assertEquals(expected, json);
    }


    @Test
    void test_convert_to_json_with_nested_object() {
        NestedRecord record = new NestedRecord(1L, new PrimitiveRecord(10L, "jon"));
        String json = JsonSerializer.toJson(record);
        String expected = "{\"id\" : 1, \"record\" : {\"id\" : 10, \"name\" : \"jon\"}}";
        Assertions.assertEquals(expected, json);
    }

    @Test
    void test_convert_to_json_with_escape_character() {
        PrimitiveRecord record = new PrimitiveRecord(1L,"hello \"world\"");
        String json = JsonSerializer.toJson(record);
        String expected = "{\"id\" : 1, \"name\" : \"hello \\\"world\\\"\"}";
        Assertions.assertEquals(expected, json);
    }

}