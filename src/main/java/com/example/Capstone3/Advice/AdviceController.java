package com.example.Capstone3.Advice;
//
//import com.example.Capstone3.Api.ApiException;
//import com.example.Capstone3.Api.ApiResponse;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.InvalidDataAccessResourceUsageException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import java.sql.SQLIntegrityConstraintViolationException;
//
//public class AdviceController {
//
//
//    @ExceptionHandler(value = ApiException.class)
//    public ResponseEntity<?> apiException(ApiException error){
//        return ResponseEntity.status(400).body(new ApiResponse(error.getMessage()));
//    }
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
//        String message= e.getFieldError().getDefaultMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<?> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
//    public ResponseEntity<?> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//
//    @ExceptionHandler(value = DataIntegrityViolationException.class)
//    public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<?> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = HttpMessageNotReadableException.class)
//    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//    @ExceptionHandler(value = NoResourceFoundException.class)
//    public ResponseEntity<?> NoResourceFoundException(NoResourceFoundException e){
//        String message= e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> IllegalArgumentException(IllegalArgumentException e){
//        String message = e.getMessage();
//        return ResponseEntity.status(400).body(new ApiResponse(message));
//    }
//}


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

@org.springframework.web.bind.annotation.ControllerAdvice //**in pdf restcontroller old version
public class AdviceController {

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