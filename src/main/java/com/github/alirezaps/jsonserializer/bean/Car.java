package com.github.alirezaps.jsonserializer.bean;


import com.github.alirezaps.jsonserializer.annotation.RenameProperty;

public class Car {
    public enum CarType {
        SEDAN, SUV, TRUCK, COUPE, CONVERTIBLE
    }
    private int id;
    private String name;
    private String color;
    @RenameProperty(name = "car_type")
    private CarType type;

    public Car() {
    }

    public Car(int id, String name, String color, CarType type) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }
}
