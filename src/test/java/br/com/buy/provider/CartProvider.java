package br.com.buy.provider;

import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import br.com.buy.domain.entity.Cart;

public class CartProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(Cart.newBuilder().total(10).uuid(UUID.randomUUID()).build())
        .map(Arguments::of);
  }


}
