package br.com.buy.domain.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.buy.domain.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
