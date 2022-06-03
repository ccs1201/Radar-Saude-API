package br.com.css.radarsaude.domain.exception.persistence;

public class EntityPersistException extends PersistException {
    public EntityPersistException(String message) {
        super(message);
    }

    public EntityPersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
