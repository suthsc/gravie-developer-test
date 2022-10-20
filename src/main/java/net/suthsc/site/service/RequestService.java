package net.suthsc.site.service;

import net.suthsc.constants.ApiConstants;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.logging.Level.FINE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Performs operations associated with REST calls to the api and
 * processes the error checking and caching portion of the result process.
 */
@Service
public class RequestService implements ApiConstants {

    private static final Logger LOGGER = Logger.getLogger(RequestService.class.getSimpleName());

    public JSONObject get(String url) throws ResponseStatusException {

        try {
            URL endpoint = new URL(url);
            var connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            var responseCode = connection.getResponseCode();
            if (responseCode == HTTP_OK) {
                try (BufferedInputStream stream = new BufferedInputStream(connection.getInputStream())) {

                    JSONTokener tokener = new JSONTokener(stream);
                    JSONObject jsonObject = new JSONObject(tokener);

                    LOGGER.log(FINE, jsonObject::toString);

                    return jsonObject;
                }

            } else {
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Search API Failed");
            }

        } catch (IOException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Remote Operation failed", e);
        }

    }
}
