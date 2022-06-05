package br.com.css.radarsaude.domain.exception.persistence;

public class EntityInUseException extends PersistException {
    public EntityInUseException(String message) {
        super(message);
    }

    public EntityInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
