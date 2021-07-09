package com.kingjakeu.lolesports.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingjakeu.lolesports.api.exception.CrawlProcessException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Crawler {
    public static Document doGetDocument(String url) {
        try {
            log.info(url);
            return Jsoup.connect(url).get();
        }catch (IOException ioException){
            log.error(ioException.getMessage(), ioException);
            throw new CrawlProcessException();
        }
    }

    public static Document doPost(String url) {
        try {
            log.info(url);
            return Jsoup.connect(url)
                    .data("query", "Java")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
        }catch (IOException ioException){
            log.error(ioException.getMessage(), ioException);
            throw new CrawlProcessException();
        }
    }

    /**
     * Crawl Web Json Data as Object without any params and header
     * @param url url
     * @param returnTypeReference return object type
     * @param <T> type
     * @return Crawled Data Object
     */
    public static <T> T doGetObject(String url,
                                    TypeReference<T> returnTypeReference) {
        return doGetObject(url, new HashMap<String, String>(), new HashMap<String, String>(), returnTypeReference);
    }

    /**
     * Crawl Web Json Data as Object
     * @param url target URL
     * @param httpHeaders configured http header
     * @param parameters request parameters
     * @param returnTypeReference return object type
     * @param <T> type
     * @return Crawled Data Object
     */
    public static <T> T doGetObject(String url,
                                    Map<String, String> httpHeaders,
                                    Map<String, String> parameters,
                                    TypeReference<T> returnTypeReference) {
        try{
            log.info(url);
            String result = HttpRequester.doGetJsonString(
                    url,
                    httpHeaders,
                    parameters
            );
            return new ObjectMapper().readValue(result, returnTypeReference);
        }catch (JsonProcessingException jsonProcessingException){
            log.error(jsonProcessingException.getMessage(), jsonProcessingException);
            throw new CrawlProcessException();
        }

    }
}