package org.example.record;


import org.example.annotation.RenameProperty;
import org.example.annotation.SkipProperty;

public record Role(@RenameProperty(name = "role_id") Long id, @SkipProperty String name) {

}
