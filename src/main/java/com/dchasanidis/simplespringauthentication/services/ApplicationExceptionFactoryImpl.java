package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.errorHandling.GlobalExceptionHandler;
import com.dchasanidis.simplespringauthentication.model.ApplicationException;
import com.dchasanidis.simplespringauthentication.model.IssueCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import static com.dchasanidis.simplespringauthentication.errorHandling.IssueCodes.INTERNAL_APPLICATION_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ApplicationExceptionFactoryImpl implements ApplicationExceptionFactory {
    private static final Logger log = getLogger(GlobalExceptionHandler.class);


    @Override
    public ApplicationException createApplicationException(IssueCode code, Throwable cause) {
        final UUID id = UUID.randomUUID();
        final String message = code.getMessage();
        if (code == INTERNAL_APPLICATION_ERROR && cause != null) {
            log.error("[{}] {}", id, cause.getMessage());
            try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
                cause.printStackTrace(pw);
                log.error(sw.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String messageText = String.format(message, id);
            return new ApplicationException(id, code, messageText, null);

        }
        String messageText = code.getMessage();
        String debugMessageText = String.format("CODE=%s, ID=%s", code.getKey(), id);
        return new ApplicationException(id, code, messageText, debugMessageText);
    }
}