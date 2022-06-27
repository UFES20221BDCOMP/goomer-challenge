package br.com.ufes.bd1.goomer.exception;

public class RestaurantDoesNotExistException extends EntityDoesNotExistException {
    public RestaurantDoesNotExistException(String message) {
        super(message);
    }
}
