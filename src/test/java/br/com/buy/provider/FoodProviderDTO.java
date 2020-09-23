package br.com.buy.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import br.com.buy.dto.FoodDataTransferObject;

public class FoodProviderDTO implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    FoodDataTransferObject foodDTO = new FoodDataTransferObject();
    foodDTO.setBaseQuantity(100);
    foodDTO.setBaseUnit("g");
    foodDTO.setCategoryId(5);
    foodDTO.setDescription("Noz, cruas");
    foodDTO.setId(597);
    foodDTO.setQuantity(10);
    return Stream.of(foodDTO).map(Arguments::of);
  }


}
