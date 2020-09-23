package br.com.buy.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.entity.Food;

public interface FoodRepository extends JpaRepository<Food, UUID> {
  Optional<Food> findByExternalId(String externalId);

  List<Food> findByCart(Cart cart);
}
