package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameLiveStatCrawlServiceTest {

    @Autowired
    GameLiveStatCrawlService gameLiveStatCrawlService;

    @Test
    void crawlGameWindowFrame() throws InterruptedException, JsonProcessingException {
        gameLiveStatCrawlService.crawlGameWindowFrame("106269654661664682");
    }
}