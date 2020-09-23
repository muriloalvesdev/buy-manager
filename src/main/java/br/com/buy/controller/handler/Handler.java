package br.com.buy.controller.handler;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.http.request.Request;
import br.com.buy.http.service.TacoService;
import br.com.buy.service.food.FoodService;

@Component
public class Handler {

  private TacoService tacoService;
  private FoodService foodService;

  public Handler(TacoService tacoService, FoodService foodService) {
    this.tacoService = tacoService;
    this.foodService = foodService;
  }

  public UUID add(Request request, String cartId) {
    Optional<FoodDataTransferObject> foodOptional =
        this.tacoService.findByDescription(request.getName());
    if (foodOptional.isPresent()) {
      return this.foodService.save(foodOptional.get(), request.getCount(), cartId);
    }
    throw new RuntimeException("Product not found!");
  }

  public CartDataTransferObject find(String cartId) {
    return this.foodService.find(cartId);
  }

  public void update(Request request, String cartId, Boolean add) {
    this.foodService.update(request.getName(), request.getCount(), cartId, add);
  }

  public void delete(String name, String cartId) {
    this.foodService.delete(name, cartId);
  }

}
