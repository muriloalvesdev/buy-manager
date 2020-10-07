package br.com.buy.service.cart;

public interface CartService<T> {
  T create(int count);

  T findById(String cartId);

  void update(T cart);
}
