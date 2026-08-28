package com.db.votacao.exceptions;

import com.db.votacao.dtos.responses.ErroResponseDTO;
import com.db.votacao.services.SessaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponseDTO> handleRunTimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro RuntimeException: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Erro Interno no Servidor",
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro Exception: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Erro Interno no Servidor",
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro NotFondException: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErroResponseDTO> handleNotFound(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro BadRequestException: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErroResponseDTO> handleConflict(
            ConflictException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro ConflictException: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro MethodArgumentNotValidException: {}", ex.getMessage());
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((erro) -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "Erro de validação nos campos informados.",
                request.getRequestURI(),
                details
        );
    }

    private ResponseEntity<ErroResponseDTO> buildResponse(
            HttpStatus status,
            String error,
            String message,
            String path,
            List<String> details
    ) {
        ErroResponseDTO response = new ErroResponseDTO(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                path,
                details
        );

        return ResponseEntity.status(status).body(response);
    }
}
