package net.suthsc.site.handler;

import java.time.Instant;
import java.util.Objects;

public class ErrorDetails {

    private final Instant timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(Instant instant, String theMessage, String theDescription) {
        timestamp = instant;
        message = theMessage;
        details = theDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetails that = (ErrorDetails) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(message, that.message) && Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, message, details);
    }

    @Override
    public String
    toString() {
        return "ErrorDetails{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
