package br.com.mtonon.product.exception.advice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.mtonon.shopping.domain.dto.ErrorDTO;
import br.com.mtonon.shopping.exception.CategoryNotFoundException;
import br.com.mtonon.shopping.exception.ProductNotFoundException;

@ControllerAdvice(basePackages = "br.com.mtonon.product.resources")
public class ProductControllerAdvice {
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProductNotFoundException.class)
	public ErrorDTO handleProductNotFound(ProductNotFoundException productNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Produto não localizado!");
		errorDTO.setTimestamp(LocalDateTime.now());
		return errorDTO;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CategoryNotFoundException.class)
	public ErrorDTO  handleCategoryNotFound(CategoryNotFoundException categoryNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Categoria não localizada!");
		errorDTO.setTimestamp(LocalDateTime.now());
		return errorDTO;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		StringBuilder sb = new StringBuilder("Valor inválido para o(s) campo(s):");
		
		for(FieldError field : fieldErrors) {
			sb.append(" ");
			sb.append(field.getField());
		}
		
		errorDTO.setMessage(sb.toString());
		errorDTO.setTimestamp(LocalDateTime.now());
		return errorDTO;
	}

}
