package com.github.alirezaps.jsonserializer.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}