package br.com.css.radarsaude.domain.exception.persistence;

import br.com.css.radarsaude.domain.exception.BusinessLogicException;

public class PersistException extends BusinessLogicException {
    public PersistException(String message) {
        super(message);
    }

    public PersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
