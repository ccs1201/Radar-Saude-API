package br.com.css.radarsaude.api.exceptionhanlder;

import br.com.css.radarsaude.domain.exception.BusinessLogicException;
import br.com.css.radarsaude.domain.exception.persistence.EntityNotFoundException;
import br.com.css.radarsaude.domain.exception.persistence.EntityPersistException;
import br.com.css.radarsaude.domain.exception.persistence.EntityUpdateException;
import br.com.css.radarsaude.domain.exception.persistence.RepositoryEntityInUseException;
import br.com.css.radarsaude.domain.model.representation.util.exception.GenericEntityUpdateMergerUtilException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandlerImpl extends ResponseEntityExceptionHandler implements ApiExceptionHandlerInterface {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> businessLogicExceptionHandler(BusinessLogicException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(RepositoryEntityInUseException.class)
    public ResponseEntity<?> entityInUseExceptionHandler(RepositoryEntityInUseException e) {

        return buildResponseEntity(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(EntityPersistException.class)
    public ResponseEntity<?> entityPersistExceptionHandler(EntityPersistException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(GenericEntityUpdateMergerUtilException.class)
    public ResponseEntity<?> entityRemoveExceptionHandler(GenericEntityUpdateMergerUtilException e) {
        return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler(EntityUpdateException.class)
    public ResponseEntity<?> entityUpdateExceptionHandler(EntityUpdateException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(body, headers, status);
    }

}
