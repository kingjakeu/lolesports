package com.kingjakeu.lolesports.api.crawl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "lolesports")
public class RiotLolEsportsConfig {
    private String url;
    private String language;
    private String xApiKey;
}
