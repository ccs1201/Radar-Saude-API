package br.com.css.radarsaude.domain.model.representation.util.exception;

import br.com.css.radarsaude.domain.exception.BusinessLogicException;

public class GenericEntityUpdateMergerUtilException extends RuntimeException {
    public GenericEntityUpdateMergerUtilException(String message) {
        super(message);
    }

    public GenericEntityUpdateMergerUtilException(Throwable cause) {
        super(cause);
    }
}
