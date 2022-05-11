package br.com.cpqd.billing.comptech.rest.handler.exception.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cpqd.billing.comptech.exception.core.rest.RestObjectNotFoundException;
import br.com.cpqd.billing.comptech.rest.handler.exception.entity.RestExceptionResponse;
import br.com.cpqd.billing.comptech.rest.handler.exception.entity.ValidationError;

/**
 * This class is responsible for handling the exception of kind {@link RestObjectNotFoundException}.
 * <p>
 * The class is annotated with {@code ControllerAdvice} that is derived from {@code Component} - Spring
 * annotation - and will be used for classes that deal with exceptions. And as the class has the
 * {@link RestController} label it handles only exceptions thrown in REST controllers.
 * </p>
 * <p>
 * The method present in the class must return a {@link RestExceptionResponse} object that has the basic
 * informations to REST response.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@RestController
@ControllerAdvice
public class RestObjectNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    // Timestamp pattern
    private static final DateTimeFormatter TIMESTAMP_PATTERN = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Method responsible for creating a new REST response when some entity object were not found on
     * application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param restObjectNotFoundException The {@link RestObjectNotFoundException} object
     * @param webRequest The {@link WebRequest} object
     * @return The custom REST response object
     */
    @ExceptionHandler(RestObjectNotFoundException.class)
    public final ResponseEntity<RestExceptionResponse> handleException(
            RestObjectNotFoundException restObjectNotFoundException, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        var validationError = new ValidationError(restObjectNotFoundException.getMessage(),
                restObjectNotFoundException.getMessage());
        lstValidationError.add(validationError);

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
