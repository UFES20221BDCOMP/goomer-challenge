package br.com.ufes.bd1.goomer.exception;

public class ProductDoesNotExistException extends EntityDoesNotExistException {
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
