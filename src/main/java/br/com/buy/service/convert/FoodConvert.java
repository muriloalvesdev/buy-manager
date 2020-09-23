package br.com.buy.service.convert;

import java.util.Arrays;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.entity.Food;
import br.com.buy.dto.FoodDataTransferObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FoodConvert {

  public static Food convert(br.com.buy.dto.FoodDataTransferObject dto, Cart cart) {
    return Food.newBuilder().baseQuantity(dto.getBaseQuantity()).baseUnit(dto.getBaseUnit())
        .cart(Arrays.asList(cart)).categoryId(dto.getCategoryId()).name(dto.getDescription())
        .externalId(dto.getId()).build();
  }

  public static Food convert(FoodDataTransferObject dto) {
    return Food.newBuilder().baseQuantity(dto.getBaseQuantity()).baseUnit(dto.getBaseUnit())
        .categoryId(dto.getCategoryId()).name(dto.getDescription()).externalId(dto.getId()).build();
  }

  public static FoodDataTransferObject convert(Food food) {
    return FoodDataTransferObject.valueOf(food);
  }
}
