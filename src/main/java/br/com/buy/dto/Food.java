package br.com.buy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Food {

  @JsonProperty("attributes")
  private String attributes;

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

  @JsonProperty("rae")
  private String retinolActivityEquivalent;

  @JsonProperty("re")
  private String retinolEquivalent;

  @JsonProperty("vitaminC")
  private String vitamin;

}
