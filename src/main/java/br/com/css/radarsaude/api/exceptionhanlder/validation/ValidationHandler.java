package br.com.css.radarsaude.api.exceptionhanlder.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

    private final MessageSource messageSource;

    public ValidationHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    public List<ValidationErrorResponse> validationsErrorHandler(MethodArgumentNotValidException e) {

        List<ValidationErrorResponse> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getFieldErrors();

        fieldErrors.forEach(error -> {
            ValidationErrorResponse validationError =
                    new ValidationErrorResponse(error.getField(),
                            messageSource.getMessage(error, LocaleContextHolder.getLocale()));

            errors.add(validationError);
        });

        return errors;

    }

}
