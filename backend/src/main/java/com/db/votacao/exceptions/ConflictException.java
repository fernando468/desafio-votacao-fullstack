package com.db.votacao.exceptions;

public class ConflictException extends Exception {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Object... args) {
        super(message.formatted(args));
    }
}
