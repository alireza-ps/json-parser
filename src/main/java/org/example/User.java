package org.example;

public record User(Long id, String name, boolean isEnable, Role role,Product product) {
}
