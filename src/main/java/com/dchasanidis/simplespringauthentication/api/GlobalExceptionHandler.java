package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.ApplicationException;
import com.dchasanidis.simplespringauthentication.model.dtos.responses.Problem;
import com.dchasanidis.simplespringauthentication.model.dtos.responses.ProblemRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleException(
            final Exception exception,
            final HttpServletRequest request
    ) {
        final String message = exception.getMessage();

        final UUID errorId;
        final String errorKey;
        final HttpStatus statusCode;

        if (exception instanceof final ApplicationException applicationException) {
            errorId = applicationException.getId();
            errorKey = applicationException.getIssueCode().getKey();
            statusCode = applicationException.getIssueCode().getResponseStatus();

            log.error("[{}] {} ### {}", errorId, message, applicationException.getDebugMessage());
        } else if (exception instanceof AccessDeniedException) {
            errorId = UUID.randomUUID();
            errorKey = "FORBIDDEN";
            statusCode = HttpStatus.FORBIDDEN;
            log.error("[{}] {}", errorId, message);
        } else {
            errorId = UUID.randomUUID();
            errorKey = "UNHANDLED_EXCEPTION";
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("[" + errorId + "] UNHANDLED_EXCEPTION: " + exception.getMessage(), exception);
        }

        final Problem errorBody = createErrorBody(errorId, errorKey, statusCode, exception, request);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.CONTENT_LANGUAGE, "en");

        return new ResponseEntity<>(errorBody, headers, statusCode);
    }

    private static Problem createErrorBody(final UUID id, final String key, final HttpStatus status, final Exception exception, final HttpServletRequest request) {
        return new Problem(id.toString(),
                key,
                status.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                new ProblemRequest(request.getRequestURI(), request.getMethod())
        );
    }
}

