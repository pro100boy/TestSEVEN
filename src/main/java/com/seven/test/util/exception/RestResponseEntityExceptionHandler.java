package com.seven.test.util.exception;

import com.seven.test.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private static Map<String, String> constraintCodeMap = new HashMap<String, String>() {
        {
            put("users_unique_email_idx", "exception.users.duplicate_email");
            put("idx_company_name_email", "exception.company.duplicate_name_email");
        }
    };

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleKeyConflict(RuntimeException ex, WebRequest request) {
        String rootMsg = ValidationUtil.getRootCause(ex).getMessage();
        if (rootMsg != null) {
            Optional<Map.Entry<String, String>> entry = constraintCodeMap.entrySet().stream()
                    .filter((it) -> rootMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                return handleExceptionInternal(ex, messageSource.getMessage(entry.get().getValue(), null, LocaleContextHolder.getLocale()),
                        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
            }
        }

        return handleExceptionInternal(ex, "DataIntegrityViolationException",
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {IllegalStateException.class, IOException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ValidationUtil.getRootCause(ex).getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {AccessDeniedException.class, NotFoundException.class})
    protected ResponseEntity<Object> accessDenied(RuntimeException ex, WebRequest request) {
        // String rootMsg = ValidationUtil.getRootCause(ex).getMessage(); // = "Access is denied"
        String bodyOfResponse = messageSource.getMessage("exception.access.denied", null, LocaleContextHolder.getLocale());//"AccessDeniedException";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleArgumentException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = messageSource.getMessage("exception.validation", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    protected ResponseEntity<Object> handleDBException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = messageSource.getMessage("exception.db", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

/*    @ExceptionHandler(MailSendException.class)
    protected ResponseEntity<Object> mailException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Email error has been occurred";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }*/
}
