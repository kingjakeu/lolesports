package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.crawl.config.RiotFeedLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.GameStatDto;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.GameTimelineStatDto;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.RefinedEventDto;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameLiveStatCrawlService {

    private final RiotFeedLolEsportsConfig feedLolEsportsConfig;

    public void crawlGameWindowFrame(String gameId){
        GameStatDto resultDto = Crawler.doGetObject(
            this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                new TypeReference<>() {}
        );
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime localDateTime = LocalDateTime.parse(resultDto.getFrames()[0].getRfc460Timestamp(), dateTimeFormatter);

        int sec = (int)(Math.ceil((double) localDateTime.getSecond() / 10) * 10);

        LocalDateTime newLocalDateTime = LocalDateTime.of(
                localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), sec, 0
        );
        GameTimelineStatDto gameTimelineStatDto = this.crawlGameDetailFrame(gameId, newLocalDateTime.toString());
        for(int i=0; i < 10; i++){
            newLocalDateTime = newLocalDateTime.plusMinutes(1L);
            GameTimelineStatDto preStatDto = gameTimelineStatDto;
            gameTimelineStatDto = this.crawlGameDetailFrame(gameId, newLocalDateTime.toString());
            RefinedEventDto refinedEventDto = gameTimelineStatDto.compareTo(preStatDto);
            if(refinedEventDto.isEventHappened()){
                System.out.println(refinedEventDto.getMessage(resultDto.getParticipantMap()));
            }
        }

    }

    public GameTimelineStatDto crawlGameDetailFrame(String gameId, String startingTime){
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("startingTime", startingTime+"Z");
        GameTimelineStatDto gameTimelineStatDto = Crawler.doGetObject(
                this.feedLolEsportsConfig.getUrl() + "/details/" + gameId,
                new HashMap<String, String>(),
                requestParameters,
                new TypeReference<>() {}
        );
        return gameTimelineStatDto;
    }
}
