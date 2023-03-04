package ru.practicum.exceptions;

import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingRequestValueException.class, MethodArgumentNotValidException.class})
    public ErrorResponse handleValidationException(Exception e) {
        String message;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException eValidation = (MethodArgumentNotValidException) e;
            message = Objects.requireNonNull(eValidation.getBindingResult().getFieldError()).getDefaultMessage();
        } else {
            message = e.getMessage();
        }
        final String REASON = "Incorrectly made request.";
        return new ErrorResponse(HttpStatus.BAD_REQUEST, REASON, message, LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({InvalidParametersException.class,
            MethodArgumentTypeMismatchException.class, NonTransientDataAccessException.class})
    public ErrorResponse handelConditionException(Exception e) {
        String message;
        if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException eValidation = (MethodArgumentTypeMismatchException) e;
            message = "Unknown state: " + eValidation.getValue().toString();
        } else {
            message = e.getMessage();
        }
        final String REASON = "For the requested operation the conditions are not met.";
        return new ErrorResponse(HttpStatus.CONFLICT, REASON, message + e.getClass(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ObjectNotFoundException.class})
    public ErrorResponse handleObjectNotFoundException(Exception e) {
        final String REASON = "The required object was not found.";
        return new ErrorResponse(HttpStatus.NOT_FOUND, REASON, e.getMessage(), LocalDateTime.now());

    }
}
