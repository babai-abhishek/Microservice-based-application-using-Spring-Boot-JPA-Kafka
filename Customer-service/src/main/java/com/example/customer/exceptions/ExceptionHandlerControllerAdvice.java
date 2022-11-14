package com.example.customer.exceptions;

import com.example.customer.model.entity.Customer;
import com.example.customer.model.entity.Response;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ComponentScan(basePackages = {"com.example.customer"})
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(NoCustomerExistException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Customer>> handleItemNotFoundException(NoCustomerExistException ex) {

        Response response = new Response();
        response.setMessage(ex.toString());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<Customer>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            String errorMsg = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMsg);
        });
        Response response = new Response();
        response.setMessage(ex.toString());
        response.setData(errors);
        response.setSuccess(false);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

  /* @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
       Map<String, String> errors = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error) -> {
           FieldError fieldError = (FieldError) error;
           String fieldName = fieldError.getField();
           String errorMsg = fieldError.getDefaultMessage();
           errors.put(fieldName, errorMsg);
       });
       Response response = new Response();
       response.setMessage(ex.toString());
       response.setData(errors);
       response.setSuccess(false);

       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
   }*/

}
