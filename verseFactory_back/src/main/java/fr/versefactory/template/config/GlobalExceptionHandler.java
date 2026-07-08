package fr.versefactory.template.config;

import fr.versefactory.template.exception.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements AccessDeniedHandler {

    @Getter
    @Builder
    public static class ErrorResponse {
        private OffsetDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorResponse> handleNotImplementedException(NotImplementedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_IMPLEMENTED;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        String message = ErrorMessages.UNSUPPORTED_MEDIA_TYPE_EXCEPTION.getMessage(ex.getContentType(),
                Optional.ofNullable(ex.getSupportedMediaTypes().toString()).orElse("")
                        .replace("["," ")
                        .replace("]",""));
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, message, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request){
        log.error("Internal Server Error: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throw new UnauthorizedException(ErrorMessages.UNAUTHORIZED_ACCESS_DENIED);
    }
}
