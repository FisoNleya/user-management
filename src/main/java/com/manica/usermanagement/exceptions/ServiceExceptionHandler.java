package com.manica.usermanagement.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundExceptionHandler(DataNotFoundException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.NOT_FOUND)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(DataHandlingException.class)
    public ResponseEntity<Object> dataHandlingExceptionHandler(DataHandlingException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(ApplicationRequestException.class)
    public ResponseEntity<Object> applicationRequestExceptionHandler(ApplicationRequestException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<Object> userManagementExceptionHandler(UserManagementException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }


    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> userManagementExceptionHandler(SignatureException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.UNAUTHORIZED)
                .build();
        log.error(customError.toString(), ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(AccessDeniedException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.FORBIDDEN)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customError);
    }

    @ExceptionHandler(TokenAuthenticationException.class)
    public ResponseEntity<Object> tokenAuthenticationExceptionHandler(TokenAuthenticationException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.UNAUTHORIZED)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customError);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> jsonProcessingExceptionHandler(JsonProcessingException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.EXPECTATION_FAILED)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(customError);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> generalSqlExceptionHandler(SQLException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.EXPECTATION_FAILED)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(customError);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<Object> parseExceptionHandler(ParseException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointerExceptionHandler(NullPointerException ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> generalExceptionHandler(Exception ex) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        log.error(customError.toString(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customError);
    }


    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        log.error(customError.toString(), ex);
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, WebRequest request) {
        ErrorMessage customError = ErrorMessage.builder()
                .eventTime(ZonedDateTime.now())
                .errorDescription(ex.getLocalizedMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();

        // Add the field error details to the error response
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            customError.setErrorDescription(customError.getErrorDescription().concat(fieldError.getField()+" : "+fieldError.getDefaultMessage()+"\n"));
        }

        return handleExceptionInternal(ex, customError, headers, HttpStatus.BAD_REQUEST, request);
    }

}
