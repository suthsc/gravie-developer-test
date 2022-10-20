package net.suthsc.site.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

@RestController
public class SiteErrorController implements ErrorController {

    private static final Logger LOGGER = Logger.getLogger(SiteErrorController.class.getSimpleName());

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        var statusCode = ((Integer) request.getAttribute("javax.servlet.error.status_code"));
        var exception = ((Exception) request.getAttribute("javax.servlet.error.exception"));
        LOGGER.log(WARNING, "status code: {0}", statusCode);
        LOGGER.log(WARNING, "Exception: {0}", exception);

        return String.format("<html><body><h2>Error Page</h2><div>Status Code: <b>%s</b></div>" +
                        "<div>Exception message: <b>%s</b></div></body></html>",
                statusCode, (exception == null) ? "N/A" : exception.getMessage());

    }

}
