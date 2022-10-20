package net.suthsc.site.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GiantBomConfiguration {

    @Value("${api.key.giant.bomb}")
    private String apiKey;

    public String getAPIKey() {
        return apiKey;
    }

}
