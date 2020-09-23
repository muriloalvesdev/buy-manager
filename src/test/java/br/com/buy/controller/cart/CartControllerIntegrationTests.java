package br.com.buy.controller.cart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.buy.domain.entity.Cart;
import br.com.buy.domain.entity.Food;
import br.com.buy.domain.repository.CartRepository;
import br.com.buy.domain.repository.FoodRepository;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.http.request.Request;
import br.com.buy.http.service.TacoService;
import br.com.buy.service.cart.CartService;

@SpringBootTest
@Profile("test")
class CartControllerIntegrationTests {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final int CATEGORY_ID = 15;
  private static final int FOOD_ID = 597;
  private static final String DESCRIPTION = "Noz, crua";

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @MockBean
  private FoodRepository foodRepository;

  @MockBean
  private CartRepository cartRepository;

  @MockBean
  private TacoService tacoService;

  @MockBean
  private CartService cartService;

  private URI baseUri;

  private FoodDataTransferObject foodDTO;

  private Request jsonRequest;

  private Cart cart;


  @BeforeEach
  void setUp() {
    this.cart = Cart.newBuilder().total(10).uuid(UUID.randomUUID()).build();
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    this.baseUri = URI.create(CartController.PATH);

    this.foodDTO = new FoodDataTransferObject();
    this.foodDTO.setCategoryId(CATEGORY_ID);
    this.foodDTO.setId(FOOD_ID);
    this.foodDTO.setDescription(DESCRIPTION);

    this.jsonRequest = new Request("Noz, crua", 10);
  }

  @Order(1)
  @Test
  void shouldAddProductInCartCreatedNow() throws Exception {
    BDDMockito.given(this.tacoService.findByDescription("Noz, crua"))
        .willReturn(Optional.of(this.foodDTO));

    BDDMockito.given(this.cartService.create(10)).willReturn(this.cart);

    this.mockMvc
        .perform(post(this.baseUri).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(this.jsonRequest)).param("cartId", ""))
        .andExpect(status().isCreated()).andExpect(header().exists("Location"));

    verify(this.tacoService, times(1)).findByDescription(Mockito.anyString());
    verify(this.cartService, times(1)).create(Mockito.anyInt());
  }

  @Order(2)
  @Test
  void shouldUpdateRemovingOneProductFromTheList() throws Exception {
    this.jsonRequest.setCount(1);
    this.mockMvc
        .perform(put(this.baseUri).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(this.jsonRequest))
            .param("cartId", this.cart.getUuid().toString()).param("add", "false"))
        .andExpect(status().isNoContent());
  }

  @Order(3)
  @Test
  void shouldUpdateAddOneProductFromTheList() throws Exception {
    this.jsonRequest.setCount(1);
    this.mockMvc
        .perform(put(this.baseUri).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(this.jsonRequest))
            .param("cartId", this.cart.getUuid().toString()).param("add", "true"))
        .andExpect(status().isNoContent());
  }

  @Order(4)
  @Test
  void shouldDeleteProductFromTheList() throws Exception {
    List<Food> foods = new ArrayList<>();
    foods.add(Food.newBuilder().name("Noz, crua").count(10).build());

    this.cart.setTotal(10);
    this.cartRepository.save(this.cart);
    this.foodRepository.saveAll(foods);

    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);
    BDDMockito.given(this.foodRepository.findByCart(this.cart)).willReturn(foods);

    this.mockMvc.perform(delete(this.baseUri).contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .content(MAPPER.writeValueAsString(this.jsonRequest))
        .param("cartId", this.cart.getUuid().toString())).andExpect(status().isNoContent());

    verify(this.foodRepository, times(1)).findByCart(Mockito.any(Cart.class));
    verify(this.cartService, times(1)).findById(Mockito.any());
  }

  @Order(5)
  @Test
  void shouldExpectedProductNotFoundWhenTryingDeleteProduct() throws Exception {
    this.mockMvc
        .perform(delete(this.baseUri).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(this.jsonRequest))
            .param("cartId", this.cart.getUuid().toString()))
        .andExpect(jsonPath("$.message",
            is("Product not found. Product has possibly been removed previously.")))
        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
        .andExpect(status().isNotFound());
  }

  @Order(6)
  @Test
  void shouldDeleteAll() throws Exception {
    BDDMockito.given(this.cartService.findById(this.cart.getUuid().toString()))
        .willReturn(this.cart);

    this.mockMvc.perform(delete(this.baseUri + "all").contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE).param("cartId", this.cart.getUuid().toString()))
        .andExpect(status().isNoContent());
  }


}
