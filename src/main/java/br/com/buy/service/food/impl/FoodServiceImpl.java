package br.com.buy.service.food.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.entity.Food;
import br.com.buy.domain.repository.FoodRepository;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.service.cart.CartService;
import br.com.buy.service.convert.FoodConvert;
import br.com.buy.service.exception.ProductNotFoundException;
import br.com.buy.service.food.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

  private FoodRepository repository;
  private CartService cartService;

  FoodServiceImpl(FoodRepository repository, CartService cartService) {
    this.repository = repository;
    this.cartService = cartService;
  }

  @Transactional
  public UUID save(FoodDataTransferObject dto, int count, String cartId) {
    if (StringUtils.isEmpty(cartId)) {
      Cart cart = addFoodInCart(dto, count);
      return cart.getUuid();
    }
    Cart cart = addFoodInCart(dto, count, cartId);
    return cart.getUuid();
  }

  @Transactional
  public void delete(String uuidCart) {
    Cart cart = findCart(uuidCart);
    cart.setTotal(0);
    List<Food> foods = this.repository.findByCart(cart);
    this.repository.deleteAll(foods);
    this.cartService.update(cart);
  }

  public CartDataTransferObject find(String cartId) {
    Cart cart = findCart(cartId);
    List<FoodDataTransferObject> foods = this.repository.findByCart(cart).stream()
        .map(FoodConvert::convert).collect(Collectors.toList());
    return new CartDataTransferObject(cart.getUuid().toString(), cart.getTotal(), foods);
  }

  public void update(String name, int count, String cartId, Boolean add) {
    Cart cart = findCart(cartId);
    this.repository.findByCart(cart).stream()
        .filter(food -> food.getName().toLowerCase().equals(name.toLowerCase())).findAny()
        .ifPresent(food -> {
          if (add) {
            food.setCount(food.getCount() + count);
            cart.setTotal(cart.getTotal() + count);
          } else {
            food.setCount(food.getCount() - count);
            cart.setTotal(cart.getTotal() - count);
          }
          this.cartService.update(cart);
          this.repository.save(food);
        });
  }

  public void delete(String name, String cartId) {
    Cart cart = findCart(cartId);
    List<Food> foods = this.repository.findByCart(cart);

    Food foodToRemove =
        foods.stream().filter(food -> food.getName().toLowerCase().equals(name.toLowerCase()))
            .findAny().orElseThrow(() -> new ProductNotFoundException(
                "Product not found. Product has possibly been removed previously."));
    this.repository.delete(foodToRemove);
    cart.setTotal(cart.getTotal() - foodToRemove.getCount());
    this.cartService.update(cart);
  }

  private Cart addFoodInCart(FoodDataTransferObject dto, int count, String cartId) {
    Cart cart = findCart(cartId);
    cart.setTotal(cart.getTotal() + count);
    Food food = createFood(dto, count, cart);
    this.cartService.update(cart);
    food.setCart(Arrays.asList(cart));
    this.repository.saveAndFlush(food);
    return cart;
  }

  private Cart findCart(String cartId) {
    return this.cartService.findById(cartId);
  }

  private Cart addFoodInCart(FoodDataTransferObject dto, int count) {
    Cart cart = createCart(count);
    Food food = createFood(dto, count, cart);
    this.repository.saveAndFlush(food);
    return cart;
  }

  private Food createFood(FoodDataTransferObject dto, int count, Cart cart) {
    Food food = FoodConvert.convert(dto, cart);
    food.setCount(count);
    return food;
  }

  private Cart createCart(int count) {
    return this.cartService.create(count);
  }
}
