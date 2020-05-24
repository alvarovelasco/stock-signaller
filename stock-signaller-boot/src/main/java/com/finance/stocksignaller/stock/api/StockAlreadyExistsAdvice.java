package com.finance.stocksignaller.stock.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.finance.stocksignaller.stock.repo.DuplicateStockException;

@ControllerAdvice
public class StockAlreadyExistsAdvice {

	  @ResponseBody
	  @ExceptionHandler(DuplicateStockException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String stockAlreadyExistsHandler(DuplicateStockException ex) {
	    return ex.getMessage();
	  }
}
