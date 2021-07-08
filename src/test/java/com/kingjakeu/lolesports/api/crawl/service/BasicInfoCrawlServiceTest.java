package com.kingjakeu.lolesports.api.crawl.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BasicInfoCrawlServiceTest {
    @Autowired
    BasicInfoCrawlService basicInfoCrawlService;

    @Autowired
    ParticipantInfoCrawlService participantInfoCrawlService;

    @Test
    @Order(1)
    void crawlLeagueInfo() {
        basicInfoCrawlService.crawlLeagueInfo();
    }

    @Test
    @Order(2)
    void crawlTournamentInfoByLeagueId() {
        basicInfoCrawlService.crawlTournamentInfoByLeagueId("98767991310872058");
    }

    @Test
    @Order(3)
    void crawlTeamInfoByLeagueId(){
        this.participantInfoCrawlService.crawlTeamInfoByLeagueId("98767991310872058");
    }

    @Test
    @Order(4)
    void crawlMatchInfoByTournamentId() {
        basicInfoCrawlService.crawlMatchInfoByTournamentId("105522984810490982");
    }
}