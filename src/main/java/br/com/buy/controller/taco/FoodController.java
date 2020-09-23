package br.com.buy.controller.taco;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.buy.dto.FoodDataTransferObject;
import br.com.buy.service.taco.TacoService;

@RestController
@RequestMapping("food")
public class FoodController {

  private TacoService tacoService;

  FoodController(TacoService tacoService) {
    this.tacoService = tacoService;
  }

  @GetMapping("/{name}")
  public ResponseEntity<List<FoodDataTransferObject>> findByDescription(@PathVariable(value = "name") String name) {
    return ResponseEntity.ok(tacoService.findByDescription(name));
  }

  @GetMapping
  public ResponseEntity<List<FoodDataTransferObject>> findById(@RequestParam(value = "id") String id) {
    return ResponseEntity.ok(tacoService.findFood(id));
  }

}