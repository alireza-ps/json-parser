package com.github.alirezaps.jsonserializer.record;

import com.github.alirezaps.jsonserializer.annotation.RenameProperty;
import com.github.alirezaps.jsonserializer.annotation.SkipProperty;

import java.util.List;
import java.util.Map;

public record Product(@RenameProperty(name = "productId") Long id,
                      @RenameProperty(name = "productType") String type,
                      @RenameProperty(name = "isProductActive") boolean isActive,
                      @SkipProperty float price,
                      @RenameProperty(name = "productCategory") Category category,
                      List<Integer> specs,
                      Role[] roles,
                      @SkipProperty Map<Integer, Role> map) {
}
