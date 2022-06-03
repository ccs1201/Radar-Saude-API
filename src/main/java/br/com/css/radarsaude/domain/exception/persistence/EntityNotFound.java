package br.com.css.radarsaude.domain.exception.persistence;

public class EntityNotFound extends PersistException{
    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
