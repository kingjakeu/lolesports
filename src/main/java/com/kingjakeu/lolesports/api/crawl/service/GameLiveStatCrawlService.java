package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingjakeu.lolesports.api.crawl.config.RiotFeedLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.GameStatDto;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.GameTimelineStatDto;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.RefinedEventDto;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameLiveStatCrawlService {

    private final RiotFeedLolEsportsConfig feedLolEsportsConfig;

    public void crawlGameWindowFrame(String gameId) throws InterruptedException, JsonProcessingException {
        ResponseEntity<String> responseEntity = Crawler.doGetResponseEntity(
                this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                new HashMap<>(),
                new HashMap<>()
        );

        if(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)){
            // do re-pub
        }else{
            GameStatDto resultDto = new ObjectMapper().readValue(responseEntity.getBody(), GameStatDto.class);
            LocalDateTime gameDateTime = resultDto.getFirstTimeFrame();
            GameTimelineStatDto gameTimelineStatDto = this.crawlGameDetailFrame(gameId, gameDateTime);
            // do pub
            for(int i=0; i < 100; i++){
                System.out.println(i);
                gameDateTime = gameDateTime.plusMinutes(1L);
                GameTimelineStatDto preStatDto = gameTimelineStatDto;
                gameTimelineStatDto = this.crawlGameDetailFrame(gameId, gameDateTime);
                if(gameTimelineStatDto == null) break;
                RefinedEventDto refinedEventDto = gameTimelineStatDto.compareTo(preStatDto);
                if(refinedEventDto.isEventHappened()){
                    System.out.println(refinedEventDto.getMessage(resultDto.getParticipantMap()));
                }
            }
        }
    }

    public GameTimelineStatDto crawlGameDetailFrame(String gameId, LocalDateTime startingTime){
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("startingTime", startingTime.toString()+"Z");
        GameTimelineStatDto gameTimelineStatDto = Crawler.doGetObject(
                this.feedLolEsportsConfig.getUrl() + "/details/" + gameId,
                new HashMap<>(),
                requestParameters,
                new TypeReference<>() {}
        );
        if(gameTimelineStatDto.getFirstTimeFrame().isBefore(startingTime)){
            // end - pub
            return null;
        }
        return gameTimelineStatDto;
    }
}
