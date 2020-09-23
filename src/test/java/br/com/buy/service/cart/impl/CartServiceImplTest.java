package br.com.buy.service.cart.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.repository.CartRepository;
import br.com.buy.provider.CartProvider;
import br.com.buy.service.exception.CartNotFoundException;

class CartServiceImplTest {

  private CartRepository repository;
  private CartServiceImpl service;

  @BeforeEach
  void setUp() {
    this.repository = spy(CartRepository.class);
    this.service = new CartServiceImpl(repository);
  }

  @ParameterizedTest
  @ArgumentsSource(CartProvider.class)
  void shouldFindById(Cart cart) {
    BDDMockito.given(this.repository.findById(cart.getUuid())).willReturn(Optional.of(cart));

    Cart cartResponse = this.service.findById(cart.getUuid().toString());

    assertEquals(cart.getUuid(), cartResponse.getUuid());
    assertEquals(cart.getTotal(), cartResponse.getTotal());

    verify(this.repository, times(1)).findById(Mockito.any());
  }

  @Test
  void shouldFindByIdButExpectedReturnCartNotFoundException() {
    UUID uuid = UUID.randomUUID();
    BDDMockito.given(this.repository.findById(uuid)).willReturn(Optional.empty());

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.findById(String.valueOf(uuid));
    });

    assertTrue(exception instanceof CartNotFoundException);
    assertEquals("cart not found with id[" + uuid + "]", exception.getMessage());

    verify(this.repository, times(1)).findById(Mockito.any());
  }

  @ParameterizedTest
  @ArgumentsSource(CartProvider.class)
  void shouldCreate(Cart cart) {
    BDDMockito.given(this.repository.saveAndFlush(Mockito.any(Cart.class))).willReturn(cart);

    Cart cartResponse = this.service.create(cart.getTotal());

    assertEquals(cart.getUuid(), cartResponse.getUuid());
    assertEquals(cart.getTotal(), cartResponse.getTotal());

    verify(this.repository, times(1)).saveAndFlush(Mockito.any(Cart.class));
  }

  @ParameterizedTest
  @ArgumentsSource(CartProvider.class)
  void shouldUpdate(Cart cart) {
    BDDMockito.given(this.repository.save(Mockito.any(Cart.class))).willReturn(cart);

    this.service.update(cart);

    verify(this.repository, times(1)).save(Mockito.any(Cart.class));
  }

}
