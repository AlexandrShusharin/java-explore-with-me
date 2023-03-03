package ru.practicum.exceptions;

public class DataExistException extends RuntimeException {
    public DataExistException(String message) {
        super(message);
    }
}
