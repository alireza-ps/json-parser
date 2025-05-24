package org.example;

import org.example.bean.Car;
import org.example.record.Category;
import org.example.record.Product;
import org.example.record.Role;
import org.example.serilizer.JsonSerializer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        Category category = new Category("service");
//        Role[] roles = {
//                new Role(1L, "role1"),
//                new Role(2L, "role2")
//        };
//        Map<Integer, Role> map = Map.of(1, new Role(10L, "jj"),
//                2, new Role(11L, "kk")
//        );
//        Product p = new Product(100L, null, true, 15.53f, category, List.of(1, 2, 3), roles, map);
//        var json = JsonSerializer.toJson(p);

        Car car = new Car(1, "BMW", "X5", null);
        String json = JsonSerializer.toJson(car);
        System.out.println(json);
    }
}