package br.com.cpqd.billing.comptech.rest.handler.exception.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cpqd.billing.comptech.rest.handler.exception.entity.RestExceptionResponse;
import br.com.cpqd.billing.comptech.rest.handler.exception.entity.ValidationError;

/**
 * This class is responsible for handling the exception of kind {@link ConstraintViolationException}.
 * <p>
 * The class is annotated with {@code ControllerAdvice} that is derived from {@code Component} - Spring
 * annotation - and will be used for classes that deal with exceptions. And as the class has the
 * {@link RestController} label it handles only exceptions thrown in REST controllers.
 * </p>
 * <p>
 * The method present in the class must return a {@link RestExceptionResponse} object that has the basic
 * informations to REST response.
 * </p>
 * <p>
 * The execution preference order was changed because this class must override the methods that handle the
 * original exceptions. To change this execution order the class is annotated with {@code Order}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@ControllerAdvice
public class RestConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler {

    // Timestamp pattern
    private static final DateTimeFormatter TIMESTAMP_PATTERN = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * @see ResponseEntityExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException,
     *      HttpHeaders, HttpStatus, WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException methodArgumentNotValidException, final HttpHeaders headers,
            final HttpStatus status, final WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error -> {
            var validationError = new ValidationError(error.getField(), error.getDefaultMessage());
            lstValidationError.add(validationError);
        });
        methodArgumentNotValidException.getBindingResult().getGlobalErrors().forEach(error -> {
            var validationError = new ValidationError(error.getObjectName(), error.getDefaultMessage());
            lstValidationError.add(validationError);
        });

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * @see ResponseEntityExceptionHandler#httpMessageNotReadableException(HttpMessageNotReadableException,
     *      HttpHeaders, HttpStatus, WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException httpMessageNotReadableException, HttpHeaders headers,
            HttpStatus status, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        var validationError = new ValidationError("", httpMessageNotReadableException.getMessage());
        lstValidationError.add(validationError);

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * @see ResponseEntityExceptionHandler#handleMissingServletRequestParameter(
     *      MissingServletRequestParameterException, HttpHeaders, HttpStatus, WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException missingServletRequestParameterException,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        var validationError = new ValidationError("", missingServletRequestParameterException.getMessage());
        lstValidationError.add(validationError);

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * @see ResponseEntityExceptionHandler#handleTypeMismatch(TypeMismatchException, HttpHeaders, HttpStatus,
     *      WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException typeMismatchException,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        var validationError = new ValidationError("", typeMismatchException.getMessage());
        lstValidationError.add(validationError);

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method responsible for creating a new REST response when some validation is made above request
     * parameter arguments on methods.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param constraintViolationException The {@link ConstraintViolationException} object
     * @param webRequest The {@link WebRequest} object
     * @return The custom REST response object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<RestExceptionResponse> handleException(
            ConstraintViolationException constraintViolationException, WebRequest webRequest) {

        final var lstValidationError = new ArrayList<ValidationError>();
        constraintViolationException.getConstraintViolations().forEach(element -> {
            var validationError = new ValidationError(
                    ((PathImpl) element.getPropertyPath()).getLeafNode().getName(), element.getMessage());
            lstValidationError.add(validationError);
        });

        var exceptionResponse = new RestExceptionResponse(LocalDateTime.now().format(TIMESTAMP_PATTERN),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), lstValidationError,
                webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
