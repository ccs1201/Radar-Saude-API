package br.com.css.radarsaude.domain.service.exception;

import br.com.css.radarsaude.domain.exception.BusinessLogicException;

public class NoRequestParameterFoundException extends BusinessLogicException {

    public NoRequestParameterFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequestParameterFoundException(String message) {
        super(message);
    }
}
