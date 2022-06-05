package br.com.css.radarsaude.api.exceptionhanlder;

import br.com.css.radarsaude.domain.exception.BusinessLogicException;
import br.com.css.radarsaude.domain.exception.persistence.EntityNotFoundException;
import br.com.css.radarsaude.domain.exception.persistence.EntityPersistException;
import br.com.css.radarsaude.domain.exception.persistence.EntityUpdateException;
import br.com.css.radarsaude.domain.exception.persistence.RepositoryEntityInUseException;
import br.com.css.radarsaude.domain.model.representation.util.exception.GenericEntityUpdateMergerUtilException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandlerImpl extends ResponseEntityExceptionHandler implements ApiExceptionHandlerInterface {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404",description = "No record found for the given parameter")
    public ResponseEntity<?> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(BusinessLogicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> businessLogicExceptionHandler(BusinessLogicException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(RepositoryEntityInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Record in use and cannot be removed")
    public ResponseEntity<?> entityInUseExceptionHandler(RepositoryEntityInUseException e) {

        return buildResponseEntity(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(EntityPersistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Record cannot be persisted")
    public ResponseEntity<?> entityPersistExceptionHandler(EntityPersistException e) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(GenericEntityUpdateMergerUtilException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Cannot merge data to persist")
    public ResponseEntity<?> genericEntityUpdateMergerUtilExceptionHandler(GenericEntityUpdateMergerUtilException e) {
        return buildResponseEntity(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(EntityUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Cannot update record")
    public ResponseEntity<?> entityUpdateExceptionHandler(EntityUpdateException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e);
    }


    @Override
    protected @NotNull ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex,
            @Nullable Object body,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {

        if (ex instanceof MethodArgumentNotValidException) {

            return new ResponseEntity<>(httpRequestMethodNotSupportedHandler((MethodArgumentNotValidException) ex, status), headers, status);

        } else if (body == null) {
            return buildResponseEntity(status, ex);

        } else {
            return new ResponseEntity<>(body, headers, status);
        }
    }

    private ApiValidationErrorResponse httpRequestMethodNotSupportedHandler(MethodArgumentNotValidException ex, HttpStatus status) {

        ApiValidationErrorResponse apiValidationErrorResponse = new ApiValidationErrorResponse();
        apiValidationErrorResponse.setStatus(status.value());
        apiValidationErrorResponse.setError(status.name());

        ex.getFieldErrors().forEach(fieldError -> apiValidationErrorResponse.getFieldValidationErrors()
                .add(apiValidationErrorResponse.new FieldValidationError(
                        fieldError.getField(), fieldError.getDefaultMessage(),
                        String.format("%s", fieldError.getRejectedValue()))));

        return apiValidationErrorResponse;
    }

}
