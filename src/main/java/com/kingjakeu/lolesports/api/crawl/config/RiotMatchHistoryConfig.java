package com.kingjakeu.lolesports.api.crawl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "leagueoflegends.acs")
public class RiotMatchHistoryConfig {

    private String url;
    private String link;

    @Getter
    private String cookie;

    public String getConvertedUrl(String linkUrl){
        return linkUrl.replace(this.link, this.url);
    }
}

