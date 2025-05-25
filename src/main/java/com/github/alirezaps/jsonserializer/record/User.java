package com.github.alirezaps.jsonserializer.record;

import java.util.List;

public record User(Long id, String name, boolean isEnable, List<Role> roles, Product product) {
}
