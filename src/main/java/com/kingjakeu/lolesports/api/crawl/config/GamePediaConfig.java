package com.kingjakeu.lolesports.api.crawl.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "gamepedia")
public class GamePediaConfig {
    private String url;
    private String tournament;
    private String matchHistory;

    public String getUrl(String league){
        return this.url
                .replace("{league}", league)
                .replace("{tournament}", this.tournament);
    }

    public String getMatchHistoryUrl(String league){
        return this.getUrl(league).replace("{component}", this.matchHistory);
    }
}
