package com.mybank.AccountService.Exception;	

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mybank.AccountService.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleDataNotFoundException(DataNotFoundException ex,HttpServletRequest request) {
		ErrorResponseDto  errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND.value() ,  ex.getMessage(),ex.getDescription(), new Date(),request.getRequestURI());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex,HttpServletRequest request) {
		ErrorResponseDto  errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value() ,  ex.getMessage(),ex.getDescription(), new Date(),request.getRequestURI());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest request) {
    ErrorResponseDto  errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value() ,ex.getMessage(),ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), new Date(),request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
