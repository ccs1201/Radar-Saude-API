package br.com.css.radarsaude.domain.exception.persistence;

public class EntityNotFoundException extends PersistException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
