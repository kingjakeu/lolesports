package com.kingjakeu.lolesports.api.crawl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "leagueoflegends.ddragon")
public class RiotGameComponentConfig {
    private String url;
    private String patchVersion;
    private String language;
    private String champion;
    private String item;
    private String rune;

    public String getChampionUrl(){
        return this.getComponentUrl(this.champion);
    }

    public String getItemUrl(){
        return this.getComponentUrl(this.item);
    }

    public String getRuneUrl(){
        return this.getComponentUrl(this.rune);
    }

    private String getUrl(){
        return this.url
                .replace("{patch-version}", this.patchVersion)
                .replace("{language-nation}", this.language);
    }

    private String getComponentUrl(String key){
        return this.getUrl()
                .replace("{component}", key);
    }
}
