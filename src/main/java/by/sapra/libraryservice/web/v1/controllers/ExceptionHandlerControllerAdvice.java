package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.exceptions.BookNotFoundException;
import by.sapra.libraryservice.services.exceptions.CategoryNotFoundException;
import by.sapra.libraryservice.web.v1.models.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getLocalizedMessage());
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getLocalizedMessage());
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        List<String> errorList = ex.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = String.join("; ", errorList);

        errorResponse.setMessage(errorMessage);

        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }
}
