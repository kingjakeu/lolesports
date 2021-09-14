package com.kingjakeu.lolesports.api.crawl.service;

import com.kingjakeu.lolesports.util.HttpRequester;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameComponentCrawlServiceTest {

    @Autowired
    GameComponentCrawlService gameComponentCrawlService;
    @Test
    void crawlChampionDataInfo() {
        this.gameComponentCrawlService.crawlChampionDataInfo();
    }

    @Test
    void crawlItemDataInfo() {
        this.gameComponentCrawlService.crawlItemDataInfo();
    }

    @Test
    void crawlRuneDataInfo() {
        this.gameComponentCrawlService.crawlRuneDataInfo();
    }
}