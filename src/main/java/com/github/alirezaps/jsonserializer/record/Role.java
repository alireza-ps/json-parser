package com.github.alirezaps.jsonserializer.record;


import com.github.alirezaps.jsonserializer.annotation.RenameProperty;
import com.github.alirezaps.jsonserializer.annotation.SkipProperty;

public record Role(@RenameProperty(name = "role_id") Long id, @SkipProperty String name) {

}
