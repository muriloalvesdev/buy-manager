package br.com.buy.controller.taco;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.buy.dto.CategoryDataTransferObject;
import br.com.buy.http.service.TacoService;

@RestController
@RequestMapping("category/")
public class CategoryController {

  private TacoService tacoService;

  CategoryController(TacoService tacoService) {
    this.tacoService = tacoService;
  }

  @GetMapping("{id}")
  public ResponseEntity<List<CategoryDataTransferObject>> findById(@PathVariable(name = "id") String id) {
    return ResponseEntity.ok(tacoService.findCategory(id));
  }

  @GetMapping
  public ResponseEntity<List<CategoryDataTransferObject>> findAllCategorys() {
    return ResponseEntity.ok(tacoService.findAllCategorys());
  }

}
