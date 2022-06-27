package br.com.ufes.bd1.goomer.exception;

public class CategoryDoesNotExistException extends EntityDoesNotExistException {
    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}
