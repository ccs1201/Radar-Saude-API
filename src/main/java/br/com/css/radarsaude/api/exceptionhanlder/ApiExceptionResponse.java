package br.com.css.radarsaude.api.exceptionhanlder;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
@Getter
@Builder
public class ApiExceptionResponse {

    @Builder.Default
    private OffsetDateTime dateTime = OffsetDateTime.now();
    private int status;
    private String error;
    private String message;

}
