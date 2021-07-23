package com.kingjakeu.lolesports.api.crawl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameLiveStatCrawlServiceTest {

    @Autowired
    GameLiveStatCrawlService gameLiveStatCrawlService;

    @Test
    void crawlGameWindowFrame() {
        gameLiveStatCrawlService.crawlGameWindowFrame("106269654661664670");
    }
}