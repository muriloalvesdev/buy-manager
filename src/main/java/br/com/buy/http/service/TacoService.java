package br.com.buy.http.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.buy.dto.CategoryDataTransferObject;
import br.com.buy.dto.FoodDataTransferObject;

@Service
public class TacoService {

  private RestTemplate restTemplate;
  private String categoryUrl;
  private String foodUrl;

  TacoService(RestTemplate restTemplate, @Value("${category.url}") String categoryUrl,
      @Value("${food.url}") String foodUrl) {
    this.restTemplate = restTemplate;
    this.categoryUrl = categoryUrl;
    this.foodUrl = foodUrl;
  }

  public List<CategoryDataTransferObject> findAllCategorys() {
    HttpHeaders httpHeaders = httpHeaders();
    HttpEntity<Object> httpEntity = httpEntity(httpHeaders);
    return Arrays.asList(this.restTemplate
        .exchange(this.categoryUrl, HttpMethod.GET, httpEntity, CategoryDataTransferObject[].class)
        .getBody());
  }

  public List<FoodDataTransferObject> findFood(String externalId) {
    return Arrays.asList(
        this.restTemplate.getForObject(this.foodUrl + externalId, FoodDataTransferObject[].class));
  }

  public List<CategoryDataTransferObject> findCategory(String categoryId) {
    return Arrays.asList(this.restTemplate.getForObject(this.categoryUrl + categoryId,
        CategoryDataTransferObject[].class));
  }

  public List<FoodDataTransferObject> findByDescription(String description) {
    return Arrays
        .asList(this.restTemplate.getForObject(this.foodUrl, FoodDataTransferObject[].class))
        .stream()
        .filter(food -> food.getDescription().toLowerCase().contains(description.toLowerCase()))
        .collect(Collectors.toList());
  }

  private HttpEntity<Object> httpEntity(HttpHeaders headers) {
    return new HttpEntity<Object>(headers);
  }

  private HttpHeaders httpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    return headers;
  }

}
