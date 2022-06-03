package br.com.css.radarsaude.domain.exception.persistence;

public class RepositoryEntityInUseException extends PersistException {
    public RepositoryEntityInUseException(String message) {
        super(message);
    }

    public RepositoryEntityInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
