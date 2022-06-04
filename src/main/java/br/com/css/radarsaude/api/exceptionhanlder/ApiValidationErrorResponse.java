package br.com.css.radarsaude.api.exceptionhanlder;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedList;
@Getter
@Setter
public final class ApiValidationErrorResponse {


    private OffsetDateTime timestamp = OffsetDateTime.now();
    private int status;
    private String error;
    private final String message = "Um ou mais campos obrigatórios não são válidos, verifique...";
    private Collection<FieldValidationError> fieldValidationErrors = new LinkedList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    public class FieldValidationError {
        private String field;
        private String fieldValidationMessage;
        private String rejectedValue;
    }
}


