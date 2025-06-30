package com.example.demo.controllers;

import com.example.demo.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String parameterValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        
        String message = String.format("Invalid parameter '%s' with value '%s'. Expected type: %s", 
                parameterName, parameterValue, requiredType);
        
        ApiResponseDTO<String> response = new ApiResponseDTO<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(400);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(400);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGenericException(Exception ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>();
        response.setSuccess(false);
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(500);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
} 