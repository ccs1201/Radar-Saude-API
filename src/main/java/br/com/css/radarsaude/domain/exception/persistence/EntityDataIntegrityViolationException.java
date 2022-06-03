package br.com.css.radarsaude.domain.exception.persistence;

import org.springframework.dao.DataIntegrityViolationException;

public class EntityDataIntegrityViolationException extends PersistException {

    public EntityDataIntegrityViolationException(String message) {
        super(message);
    }

    public EntityDataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
