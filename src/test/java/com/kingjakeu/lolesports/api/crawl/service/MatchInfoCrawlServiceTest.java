package com.kingjakeu.lolesports.api.crawl.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MatchInfoCrawlServiceTest {
    @Autowired
    MatchInfoCrawlService matchInfoCrawlService;

    @Autowired
    ParticipantInfoCrawlService participantInfoCrawlService;

    @Test
    @Order(1)
    void crawlLeagueInfo() {
        this.matchInfoCrawlService.crawlLeagueInfo();
    }

    @Test
    @Order(2)
    void crawlTournamentInfoByLeagueId() {
        this.matchInfoCrawlService.crawlTournamentInfoByLeagueId("98767991310872058");
    }

    @Test
    @Order(3)
    void crawlTeamInfoByLeagueId(){
        this.participantInfoCrawlService.crawlParticipantInfoByLeagueId("98767991310872058");
    }

    @Test
    @Order(4)
    void crawlMatchInfoByTournamentId() {
        this.matchInfoCrawlService.crawlMatchInfoByTournamentId("106269654659501670");
    }

    @Test
    @Order(5)
    void crawlGameInfoByMatchId() {
        this.matchInfoCrawlService.crawlGameInfoByMatchId("106269654661205609");
    }

    @Test
    @Order(6)
    void crawlMatchHistoryLinkByGameId() {
        this.matchInfoCrawlService.crawlMatchHistoryLinkByGameId("106269654661205610");
    }
}