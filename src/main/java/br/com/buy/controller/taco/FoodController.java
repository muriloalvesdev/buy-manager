package br.com.buy.controller.taco;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.buy.controller.handler.Handler;
import br.com.buy.dto.FoodDataTransferObject;

@RestController
@RequestMapping(FoodController.PATH)
class FoodController {

  static final String PATH = "/food/";

  private Handler handler;

  FoodController(Handler handler) {
    this.handler = handler;
  }

  @GetMapping("/{name}")
  public ResponseEntity<List<FoodDataTransferObject>> findByDescription(
      @PathVariable(value = "name") String name) {
    return ResponseEntity.ok(this.handler.findByDescription(name));
  }

  @GetMapping
  public ResponseEntity<List<FoodDataTransferObject>> findById(
      @RequestParam(value = "id") String id) {
    return ResponseEntity.ok(this.handler.findFoodById(id));
  }

}
