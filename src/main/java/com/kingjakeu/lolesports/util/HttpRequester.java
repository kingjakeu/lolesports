package com.kingjakeu.lolesports.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class HttpRequester {

    /**
     * GET Json Object as String from requested URL
     *
     * @param url requesting url
     * @param httpHeader request http header
     * @param queryParams request params
     * @return result json objectString
     */
    public static ResponseEntity<String> doGetJsonString(String url,
                                         Map<String, String> httpHeader,
                                         Map<String, String> queryParams) {
        // Create Get-WebClient
        WebClient webClient = createDefaultGetJsonClient(url);
        WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();

        // Set Request parameters
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(queryParams);

        // Set URI with parameters
        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(
                uriBuilder -> uriBuilder
                        .queryParams(params)
                        .build()
        );

        // Set Http Header
        for(Map.Entry<String, String> header : httpHeader.entrySet()){
            headersSpec = headersSpec.header(header.getKey(), header.getValue());
        }

        // Send GET Request and get response as String
        Mono<ResponseEntity<String>> entityMono = headersSpec
                .retrieve()
                .toEntity(String.class);

        return entityMono.block();
    }

    /**
     * Create Web Client for Json Request
     * @param url target url
     * @return Json WebClient
     */
    private static WebClient createDefaultGetJsonClient(String url){
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}