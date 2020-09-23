package br.com.buy.controller.taco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import br.com.buy.dto.FoodDataTransferObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile("test")
public class FoodControllerIntegrationTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private static final int ID = 597;
  private static final int CATEGORY_ID = 15;
  private static final String DESCRIPTION = "Noz, crua";
  private static final String PATH_VARIABLE = "Noz";

  private URI pathUriByDescription;
  private URI pathUriById;

  @BeforeEach
  void setUp() {
    this.pathUriByDescription = URI.create(FoodController.PATH + PATH_VARIABLE);
    this.pathUriById = URI.create(FoodController.PATH + "?id=" + ID);
  }

  @Test
  void shouldFindById() {
    FoodDataTransferObject[] foodDataTransferObjects =
        this.restTemplate.getForObject(this.pathUriById, FoodDataTransferObject[].class);
    assertNotNull(foodDataTransferObjects[0]);
    asserts(foodDataTransferObjects[0]);
  }

  @Test
  void shouldFindByDescription() {
    FoodDataTransferObject[] foodDataTransferObjects =
        this.restTemplate.getForObject(this.pathUriByDescription, FoodDataTransferObject[].class);
    assertNotNull(foodDataTransferObjects[0]);
    asserts(foodDataTransferObjects[0]);
  }

  private void asserts(FoodDataTransferObject foodDataTransferObject) {
    assertEquals(ID, foodDataTransferObject.getId());
    assertEquals(CATEGORY_ID, foodDataTransferObject.getCategoryId());
    assertEquals(DESCRIPTION, foodDataTransferObject.getDescription());
  }

}
