package br.com.buy.service.cart;

import br.com.buy.domain.entity.Cart;

public interface CartService {
  Cart create(int count);
  Cart findById(String cartId);
  void update(Cart cart);
}
