package br.com.buy.controller.cart;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.buy.controller.handler.Handler;
import br.com.buy.dto.CartDataTransferObject;
import br.com.buy.http.request.Request;

@RestController
@RequestMapping(CartController.PATH)
public class CartController {

  static final String PATH = "/cart/";

  private Handler handler;

  public CartController(Handler handler) {
    this.handler = handler;
  }

  @PostMapping
  public ResponseEntity<Object> add(@RequestBody @Validated Request request,
      @RequestParam(name = "cartId", required = false) String cartId) {
    UUID uuidCart = this.handler.add(request, cartId);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("cart/{idCart}").buildAndExpand(uuidCart).toUri()).build();
  }

  @PutMapping
  public ResponseEntity<Object> update(@RequestBody @Validated Request request,
      @RequestParam(name = "cartId") String cartId, @RequestParam(name = "add") Boolean add) {
    this.handler.update(request, cartId, add);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestBody @Validated Request request,
      @RequestParam(name = "cartId") String uuidCart) {
    this.handler.delete(request.getName(), uuidCart);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{uuidCart}")
  public ResponseEntity<CartDataTransferObject> find(
      @PathVariable(name = "uuidCart") String uuidCart) {
    return ResponseEntity.ok(this.handler.find(uuidCart));
  }
}
