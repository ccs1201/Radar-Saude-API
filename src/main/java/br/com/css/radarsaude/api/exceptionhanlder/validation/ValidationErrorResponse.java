package br.com.css.radarsaude.api.exceptionhanlder.validation;

public class ValidationErrorResponse {

    private final String field;
    private final String error;

    public ValidationErrorResponse(String attribute, String error) {
        this.field = attribute;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
