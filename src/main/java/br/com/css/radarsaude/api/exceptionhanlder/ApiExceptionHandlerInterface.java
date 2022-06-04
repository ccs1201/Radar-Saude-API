package br.com.css.radarsaude.api.exceptionhanlder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiExceptionHandlerInterface {

    private ApiExceptionResponse buildResponse(Exception e, HttpStatus httpStatus) {
        return ApiExceptionResponse.builder().message(e.getLocalizedMessage())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .build();
    }

    default ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception exception) {
        return ResponseEntity.status(httpStatus).body(buildResponse(exception, httpStatus));

    }
}
