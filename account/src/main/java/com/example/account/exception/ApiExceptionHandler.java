package com.example.account.exception;

import com.example.account.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorsList = ex.getBindingResult().getAllErrors();

        validationErrorsList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleApiException(Exception e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExits(CustomerAlreadyExistsException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
