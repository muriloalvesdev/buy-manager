package br.com.buy.controller.taco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import br.com.buy.dto.CategoryDataTransferObject;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile("test")
class CategoryControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static final int ID = 1;
  private static final String CATEGORY = "Cereais e derivados";
  private URI pathUriById;
  private URI baseUri;

  @BeforeEach
  void setUp() {
    this.pathUriById = URI.create(CategoryController.PATH + ID);
    this.baseUri = URI.create(CategoryController.PATH);
  }

  @Test
  void shouldFindAll() {
    CategoryDataTransferObject[] categoryDataTransferObjects =
        this.restTemplate.getForObject(this.baseUri, CategoryDataTransferObject[].class);
    assertNotEquals(0, categoryDataTransferObjects.length);
  }

  @Test
  void shouldFindById() {
    CategoryDataTransferObject[] response =
        this.restTemplate.getForObject(this.pathUriById, CategoryDataTransferObject[].class);

    assertNotNull(response[0]);

    CategoryDataTransferObject categoryDataTransferObject = response[0];
    assertEquals(ID, categoryDataTransferObject.getId());
    assertEquals(CATEGORY, categoryDataTransferObject.getCategory());
  }
}
