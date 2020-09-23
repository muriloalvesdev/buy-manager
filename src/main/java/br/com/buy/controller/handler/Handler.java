package br.com.buy.controller.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.http.request.resource.Request;
import br.com.buy.http.request.service.TacoService;
import br.com.buy.service.exception.FoodNotFoundException;
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

  public CartDataTransferObject findCartById(String cartId) {
    return this.foodService.find(cartId);
  }

  public void update(Request request, String cartId, Boolean add) {
    this.foodService.update(request.getName(), request.getCount(), cartId, add);
  }

  public void delete(String name, String cartId) {
    this.foodService.delete(name, cartId);
  }

  public List<FoodDataTransferObject> findFoodById(String foodId) {
    return this.tacoService.findFood(foodId);
  }

  public List<FoodDataTransferObject> findByDescription(String name) {
    FoodDataTransferObject foodDataTransferObject = this.tacoService.findByDescription(name)
        .orElseThrow(() -> new FoodNotFoundException("Food with name [" + name + "] not found!"));
    return Arrays.asList(foodDataTransferObject);
  }

  public void delete(String uuidCart) {
    this.foodService.delete(uuidCart);
  }
}
