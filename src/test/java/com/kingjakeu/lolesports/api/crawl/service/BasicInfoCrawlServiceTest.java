package com.kingjakeu.lolesports.api.crawl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BasicInfoCrawlServiceTest {
    @Autowired
    BasicInfoCrawlService basicInfoCrawlService;

    @Test
    void crawlLeagueInfo() {
        basicInfoCrawlService.crawlLeagueInfo();
    }
}