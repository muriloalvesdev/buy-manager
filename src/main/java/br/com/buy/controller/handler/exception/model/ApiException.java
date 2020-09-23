package br.com.buy.controller.handler.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiException {
  private String message;
  private int status;

}
