package com.github.alirezaps.jsonserializer.record;

import com.github.alirezaps.jsonserializer.annotation.RenameProperty;

public record Category(@RenameProperty(name = "categoryTitle") String title) {
}
