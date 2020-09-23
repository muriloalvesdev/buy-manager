package br.com.buy.service.food;

import java.util.UUID;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;

public interface FoodService {
  UUID save(FoodDataTransferObject dto, int count, String cartId);

  CartDataTransferObject find(String cartId);

  void update(String name, int count, String cartId, Boolean add);

  void delete(String name, String cartId);
}
