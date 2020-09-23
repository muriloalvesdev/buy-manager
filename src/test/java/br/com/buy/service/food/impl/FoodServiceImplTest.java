package br.com.buy.service.food.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.entity.Food;
import br.com.buy.domain.repository.FoodRepository;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.provider.FoodProviderDTO;
import br.com.buy.service.cart.CartService;
import br.com.buy.service.convert.FoodConvert;
import br.com.buy.service.exception.ProductNotFoundException;

public class FoodServiceImplTest {

  private FoodRepository repository;

  private CartService cartService;

  private FoodServiceImpl service;

  private Cart cart;

  @BeforeEach
  void setUp() {
    this.cartService = spy(CartService.class);
    this.repository = spy(FoodRepository.class);

    this.cart = Cart.newBuilder().uuid(UUID.randomUUID()).total(10).build();
    this.service = new FoodServiceImpl(repository, cartService);
  }

  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldSaveByCartId(FoodDataTransferObject dto) {
    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    this.service.save(dto, 10, this.cart.getUuid().toString());

    verify(this.cartService, times(1)).findById(Mockito.any());
  }

  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldSaveWithCartIdEmpty(FoodDataTransferObject dto) {
    BDDMockito.given(this.cartService.create(10)).willReturn(this.cart);

    UUID uuid = this.service.save(dto, 10, "");

    assertEquals(this.cart.getUuid(), uuid);

    verify(this.cartService, times(1)).create(Mockito.anyInt());
  }

  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldDeleteByCartId(FoodDataTransferObject dto) {
    List<Food> foods = new ArrayList<>();
    foods.add(FoodConvert.convert(dto));

    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    BDDMockito.given(this.repository.findByCart(this.cart)).willReturn(foods);

    this.service.delete(this.cart.getUuid().toString());

    verify(this.cartService, times(1)).findById(Mockito.anyString());
    verify(this.repository, times(1)).findByCart(Mockito.any(Cart.class));
  }


  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldDeleteByName(FoodDataTransferObject dto) {
    List<Food> foods = new ArrayList<>();
    foods.add(FoodConvert.convert(dto));

    BDDMockito.given(this.repository.findByCart(this.cart)).willReturn(foods);
    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    this.service.delete(dto.getDescription(), this.cart.getUuid().toString());

    verify(this.repository, times(1)).findByCart(Mockito.any(Cart.class));
    verify(this.cartService, times(1)).findById(Mockito.anyString());
  }

  @Test
  void shouldDeleteButReturnProductNotFoundException() {
    List<Food> foods = new ArrayList<>();

    BDDMockito.given(this.repository.findByCart(this.cart)).willReturn(foods);
    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.delete("anything", this.cart.getUuid().toString());
    });

    assertTrue(exception instanceof ProductNotFoundException);
    assertEquals("Product not found. Product has possibly been removed previously.",
        exception.getMessage());
  }

  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldUdateAddValueAndRemoveValueInAttributeCount(FoodDataTransferObject dto) {
    List<Food> foods = new ArrayList<>();
    foods.add(FoodConvert.convert(dto));

    BDDMockito.given(this.repository.findByCart(this.cart)).willReturn(foods);
    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    this.service.update(dto.getDescription(), 2, this.cart.getUuid().toString(), true);
    this.service.update(dto.getDescription(), 2, this.cart.getUuid().toString(), false);

    verify(this.repository, times(2)).findByCart(Mockito.any(Cart.class));
    verify(this.cartService, times(2)).findById(Mockito.any());
  }

  @ParameterizedTest
  @ArgumentsSource(FoodProviderDTO.class)
  void shouldFindCartById(FoodDataTransferObject dto) {
    List<Food> foods = new ArrayList<>();
    foods.add(FoodConvert.convert(dto));

    BDDMockito.given(this.repository.findByCart(this.cart)).willReturn(foods);

    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    CartDataTransferObject cartDataTransferObject =
        this.service.find(this.cart.getUuid().toString());

    List<FoodDataTransferObject> foodDTOs = cartDataTransferObject.getFoods();

    assertEquals(this.cart.getUuid().toString(), cartDataTransferObject.getCartId());
    assertEquals(this.cart.getTotal(), cartDataTransferObject.getTotal());

    assertNotNull(foodDTOs.get(0));
    assertEquals(1, foodDTOs.size());

    FoodDataTransferObject foodDataTransferObject = foodDTOs.get(0);

    assertEquals(dto.getBaseUnit(), foodDataTransferObject.getBaseUnit());
    assertEquals(dto.getCategoryId(), foodDataTransferObject.getCategoryId());
    assertEquals(dto.getDescription(), foodDataTransferObject.getDescription());
    assertEquals(dto.getId(), foodDataTransferObject.getId());

    verify(this.repository, times(1)).findByCart(Mockito.any(Cart.class));
    verify(this.cartService, times(1)).findById(Mockito.any());
  }

}
