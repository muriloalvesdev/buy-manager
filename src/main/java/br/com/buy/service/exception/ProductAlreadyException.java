package br.com.buy.service.exception;

public class ProductAlreadyException extends RuntimeException {
  public ProductAlreadyException() {
    super(
        "Product has already been added to the cart, you can use the API update, so you can change the quantity of this product.");
  }
}
