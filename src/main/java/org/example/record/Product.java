package org.example.record;

import java.util.List;
import java.util.Map;

public record Product(Long id, String type, boolean isActive, float price, Category category, List<Integer> specs,
                      Role[] roles, Map<Integer,Role> map) {
}
