package br.com.buy.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CartDataTransferObject {

  @JsonProperty("cart_uuid")
  private String cartId;

  @JsonProperty("total")
  private int total;

  private List<FoodDataTransferObject> foods;

}
