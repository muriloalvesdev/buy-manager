package br.com.buy.http.request.resource;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Request {

  @NotNull(message = "name is required!")
  @JsonProperty
  private String name;

  @NotNull(message = "name is required!")
  @JsonProperty
  private int count;

}
