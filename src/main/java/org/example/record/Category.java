package org.example.record;

import org.example.annotation.RenameProperty;

public record Category(@RenameProperty(name = "categoryTitle") String title) {
}
