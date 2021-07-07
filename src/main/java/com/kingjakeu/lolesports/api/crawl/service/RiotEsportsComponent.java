package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.crawl.config.RiotLolEsportsConfig;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RiotEsportsComponent {

    private final RiotLolEsportsConfig esportsConfig;

    /**
     * Create Default Request Http Header for Lol Esports API
     * @return Default Request Http Header
     */
    public Map<String, String> createDefaultLolEsportsHttpHeader(){
        Map<String, String> header = new HashMap<>();
        header.put("x-api-key", this.esportsConfig.getXApiKey());
        return header;
    }

    /**
     * Create Default Request Parameter for Lol Esports API
     * @return Default Request Parameter
     */
    public Map<String, String> createDefaultLolEsportParameters(){
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("hl", this.esportsConfig.getLanguage());
        return parameters;
    }

    public <T> T crawlLolEsportApi(String uri, Map<String, String> parameters, TypeReference<T> returnType){
        String url = this.esportsConfig.getUrl() + uri;
        return Crawler.doGetObject(url, this.createDefaultLolEsportsHttpHeader(), parameters, returnType);
    }
}
