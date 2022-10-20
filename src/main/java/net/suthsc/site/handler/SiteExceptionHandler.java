package net.suthsc.site.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

@ControllerAdvice
public class SiteExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(SiteExceptionHandler.class.getSimpleName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        var details = new ErrorDetails(Instant.now(), exception.getMessage(), request.getDescription(false));
        LOGGER.log(WARNING, details::toString);
        LOGGER.log(WARNING, request.getDescription(true));

        return new ResponseEntity<>(details,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
