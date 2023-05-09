package com.myloanz.partnership.exception;
 
import com.myloanz.partnership.api.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;
 
@RestControllerAdvice
public class ApiExceptionHandler {
 
    @ExceptionHandler({ArithmeticException.class})
    ResponseEntity<ExceptionResponse> handleArithmeticException(ArithmeticException e) {
        var response = new ExceptionResponse();
        response.setSummary("Got arithmetic exception");
        response.setMessage(e.getMessage());
 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
 
    @ExceptionHandler({HttpMessageNotReadableException.class})
    ResponseEntity<ExceptionResponse> handleRequestBodyNotReadable(HttpMessageNotReadableException e) {
        var response = new ExceptionResponse();
        response.setSummary("Cannot read HTTP request body. Check whether you submit valid JSON.");
        response.setMessage(e.getMessage());
 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
 
    @ExceptionHandler({Exception.class})
    ResponseEntity<ExceptionResponse> handleAnyException(Exception e) {
        var response = new ExceptionResponse();
        response.setSummary("Got exception");
        response.setMessage(e.getClass().toString() + " : " + e.getMessage());
 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler({LoanBusinessException.class})
    ResponseEntity<ExceptionResponse> handleLoanBusinessException(LoanBusinessException e) {
        var response = new ExceptionResponse();
        response.setSummary("Got loan business process exception");
        response.setMessage(e.getMessage());
     
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } 
    
    @ExceptionHandler({LoanOwnerException.class})
    ResponseEntity<ExceptionResponse> handleLoanOwnerException(LoanOwnerException e) {
        var response = new ExceptionResponse();
        response.setSummary("This loan is forbidden");
        response.setMessage(e.getMessage());
 
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var message = e
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + " (value : " + f.getRejectedValue() + ") " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
     
        var response = new ExceptionResponse();
        response.setSummary("Invalid input arguments");
        response.setMessage(message);
     
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
 
}