package com.example.Capstone3.Advice;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> apiException(ApiException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }
    //id passed
    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<?> StaleObjectStateException(StaleObjectStateException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException error){
        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
    }




}