package ru.practicum.exceptions;

public class InvalidParametersException extends RuntimeException {
    public InvalidParametersException(String message) {
        super(message);
    }
}
