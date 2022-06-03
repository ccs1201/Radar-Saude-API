package br.com.css.radarsaude.domain.exception.persistence;

public class EntityUpdateException extends PersistException{
    public EntityUpdateException(String message) {
        super(message);
    }

    public EntityUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
