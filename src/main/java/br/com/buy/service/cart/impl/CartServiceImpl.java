package br.com.buy.service.cart.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.repository.CartRepository;
import br.com.buy.service.cart.CartService;

@Service
public class CartServiceImpl implements CartService {

  private CartRepository repository;

  CartServiceImpl(CartRepository repository) {
    this.repository = repository;
  }

  public Cart findById(String cartId) {
    return this.repository.findById(UUID.fromString(cartId))
        .orElseThrow(() -> new RuntimeException("cart not found with id[" + cartId + "]"));
  }

  public Cart create(int count) {
    return this.repository.saveAndFlush(Cart.newBuilder().total(count).build());
  }

  @Override
  public void update(Cart cart) {
    this.repository.save(cart);
  }
}