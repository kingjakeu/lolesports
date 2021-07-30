package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingjakeu.lolesports.api.live.dto.LiveGameStatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameLiveStatCrawlServiceTest {

    @Autowired
    GameLiveStatCrawlService gameLiveStatCrawlService;

    @Test
    void crawlGameWindowFrame() throws InterruptedException, JsonProcessingException {
        gameLiveStatCrawlService.test("106269654661730246");
    }

    @Test
    void test(){
        try{
            while(true){
                LiveGameStatDto liveGameStatDto = this.gameLiveStatCrawlService.crawlGameStartTimeFrame("106269654661730246");
                if(!liveGameStatDto.isEmptyGameFrameDateTime()){
                    System.out.println(liveGameStatDto.getGameFrameDateTime());
                    break;
                }
                Thread.sleep(30000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Test
    void getCurrentDateTime() {
        System.out.println("2021-07-30T08:06:30");
        System.out.println(this.gameLiveStatCrawlService.getCurrentDateTime());

    }
}