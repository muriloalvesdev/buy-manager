package br.com.buy.http.request;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Request {

  @NotNull(message = "name is required!")
  @JsonProperty
  private String name;

  @NotNull(message = "name is required!")
  @JsonProperty
  private int count;

}
