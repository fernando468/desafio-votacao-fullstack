package com.db.votacao.exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object... args) {
        super(message.formatted(args));
    }
}
