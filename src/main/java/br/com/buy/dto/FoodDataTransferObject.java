package br.com.buy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.buy.domain.entity.Food;
import lombok.Data;

@Data
public class FoodDataTransferObject {

  @JsonIgnore
  private Object attributes;

  @JsonProperty("base_qty")
  private int baseQuantity;

  @JsonProperty("base_unit")
  private String baseUnit;

  @JsonProperty("category_id")
  private int categoryId;

  @JsonProperty("description")
  private String description;

  @JsonProperty("id")
  private int id;

  @JsonProperty("quantity")
  private int quantity;

  public static FoodDataTransferObject valueOf(Food food) {
    FoodDataTransferObject dto = new FoodDataTransferObject();
    dto.setAttributes(food.getAttributes().toString());
    dto.setBaseQuantity(food.getBaseQuantity());
    dto.setBaseUnit(food.getBaseUnit());
    dto.setCategoryId(food.getCategoryId());
    dto.setDescription(food.getName());
    dto.setId(food.getExternalId());
    dto.setQuantity(food.getCount());
    return dto;
  }

}
