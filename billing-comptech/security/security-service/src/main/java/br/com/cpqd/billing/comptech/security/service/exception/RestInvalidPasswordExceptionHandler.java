package br.com.cpqd.billing.comptech.security.service.exception;

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

import br.com.cpqd.billing.comptech.rest.handler.exception.entity.RestExceptionResponse;
import br.com.cpqd.billing.comptech.rest.handler.exception.entity.ValidationError;
import br.com.cpqd.billing.comptech.security.service.UserService;

/**
 * This class is responsible for handling the exception of kind {@link RestInvalidPasswordException}.
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
public class RestInvalidPasswordExceptionHandler extends ResponseEntityExceptionHandler {

    // Timestamp pattern
    private static final DateTimeFormatter TIMESTAMP_PATTERN = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Method responsible for creating a new REST response when the user password is invalid on {@code create}
     * or {@code update} methods of the {@link UserService} class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param restInvalidPassordException The {@link RestInvalidPasswordException} object
     * @param webRequest The {@link WebRequest} object
     * @return The custom REST response object
     */
    @ExceptionHandler(RestInvalidPasswordException.class)
    public final ResponseEntity<RestExceptionResponse> handleException(
            RestInvalidPasswordException restInvalidPassordException, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        var validationError = new ValidationError(restInvalidPassordException.getField(),
                restInvalidPassordException.getMessage());
        lstValidationError.add(validationError);

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
