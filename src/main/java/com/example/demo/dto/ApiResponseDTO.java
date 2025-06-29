package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("status_code")
    private int statusCode;
    
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, "Success", data, LocalDateTime.now(), 200);
    }
    
    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(true, message, data, LocalDateTime.now(), 200);
    }
    
    public static <T> ApiResponseDTO<T> error(String message, int statusCode) {
        return new ApiResponseDTO<>(false, message, null, LocalDateTime.now(), statusCode);
    }
    
    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(false, message, null, LocalDateTime.now(), 400);
    }
} 