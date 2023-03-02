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
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingRequestValueException.class,
            NonTransientDataAccessException.class})
    public ErrorResponse handleValidationException(Exception e) {
        String message;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException eValidation = (MethodArgumentNotValidException) e;
            message = Objects.requireNonNull(eValidation.getBindingResult().getFieldError()).getDefaultMessage();
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException eValidation = (MethodArgumentTypeMismatchException) e;
            message = "Unknown state: " + eValidation.getValue().toString();
        } else {
            message = e.getMessage();
        }
        final String REASON = "The required object was not found.";
        return new ErrorResponse(HttpStatus.BAD_REQUEST, REASON, message, LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ObjectNotFoundException.class})
    public ErrorResponse handleObjectNotFoundException(Exception e) {
        final String REASON = "The required object was not found.";
        return new ErrorResponse(HttpStatus.NOT_FOUND, REASON, e.getMessage(), LocalDateTime.now());

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        final String REASON = "Internal server error.";
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, REASON,
                "Undefined error" + e.getClass(), LocalDateTime.now());
    }
}
