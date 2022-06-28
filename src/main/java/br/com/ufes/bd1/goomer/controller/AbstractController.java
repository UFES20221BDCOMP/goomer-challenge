package br.com.ufes.bd1.goomer.controller;

import br.com.ufes.bd1.goomer.exception.EntityDoesNotExistException;
import br.com.ufes.bd1.goomer.exception.ProvidedDataInconsistencyException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public class AbstractController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityDoesNotExistException.class, ProvidedDataInconsistencyException.class})
    public Map<String, Object> handleEntityDoesNotExist(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    public Map<String, Object> handlePersistenceException(PersistenceException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        Map<String, String> fields = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            fields.put(camelToSnakeCase(fieldName), errorMessage);
        });
        error.put("error", "Invalid JSON");
        error.put("fields", fields);
        return error;
    }

    private static String camelToSnakeCase(String s) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return s.replaceAll(regex, replacement).toLowerCase();
    }

}
