package com.kingjakeu.lolesports.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.Map;

@Slf4j
public class Crawler {
    public static Document doGetDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static Document doPost(String url) throws IOException {
        return Jsoup.connect(url)
                .data("query", "Java")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
    }

    /**
     * Crawl Web Json Data as Object
     * @param url target URL
     * @param httpHeaders configured http header
     * @param parameters request parameters
     * @param returnTypeReference return object type
     * @param <T> type
     * @return Crawled Data Object
     * @throws JsonProcessingException error while convert string to object
     */
    public static <T> T doGetObject(String url,
                                    Map<String, String> httpHeaders,
                                    Map<String, String> parameters,
                                    TypeReference<T> returnTypeReference) {
        try{
            String result = HttpRequester.doGetJsonString(
                    url,
                    httpHeaders,
                    parameters
            );
            return new ObjectMapper().readValue(result, returnTypeReference);
        }catch (JsonProcessingException jsonProcessingException){
            throw new RuntimeException("Error");
        }

    }
}