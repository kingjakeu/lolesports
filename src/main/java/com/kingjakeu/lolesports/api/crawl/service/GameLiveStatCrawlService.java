package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingjakeu.lolesports.api.crawl.config.RiotFeedLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.*;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventDto;
import com.kingjakeu.lolesports.api.live.dto.LiveGameStatDto;
import com.kingjakeu.lolesports.api.live.service.LiveStatRedisMapper;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameLiveStatCrawlService {

    private final LiveStatRedisMapper liveStatRedisMapper;
    private final RiotFeedLolEsportsConfig feedLolEsportsConfig;

    public void test(String gameId) throws JsonProcessingException, InterruptedException {
        LiveGameStatDto liveGameStatDto = this.crawlGameStartTimeFrame(gameId);
        if(!liveGameStatDto.isEmptyGameFrameDateTime()){
            while (true){
                String gameFrame = liveGameStatDto.getGameFrameDateTime();
                liveGameStatDto = this.crawlGameLiveStatByFrame(gameId);
                if(gameFrame.equals(liveGameStatDto.getGameFrameDateTime())){
                    break;
                }
                Thread.sleep(10000);
            }
        }
    }

    /**
     * Crawl Game started with start time frame value
     * @param gameId game ID
     * @throws JsonProcessingException
     */
    public LiveGameStatDto crawlGameStartTimeFrame(String gameId) throws JsonProcessingException {
        // get game live stat info from redis
        LiveGameStatDto liveGameStatDto = this.liveStatRedisMapper.getLiveGameStat(gameId);

        // if not exist, create one
        if(liveGameStatDto == null){
            liveGameStatDto = new LiveGameStatDto();
        }

        // if game frame time not exist
        if(liveGameStatDto.isEmptyGameFrameDateTime()){
            ResponseEntity<String> responseEntity = Crawler.doGetResponseEntity(
                    this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                    new HashMap<>(),
                    new HashMap<>()
            );
            // get OK when game started, 204 NO CONTENT when not started
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                // convert result to game live stat info
                GameStatDto resultDto = new ObjectMapper().readValue(responseEntity.getBody(), GameStatDto.class);
                liveGameStatDto = resultDto.toLiveGameStatDto();
            }
        }
        // save game live stat info on redis
        this.liveStatRedisMapper.saveLiveGameStat(gameId, liveGameStatDto);
        return liveGameStatDto;
    }

    /**
     * Crawl Game live stat info by timeframe
     * @param gameId game ID
     */
    public LiveGameStatDto crawlGameLiveStatByFrame(String gameId) {
        // get live stat game info from redis
        LiveGameStatDto liveGameStatDto = this.liveStatRedisMapper.getLiveGameStat(gameId);

        if(!liveGameStatDto.isEmptyGameFrameDateTime()){
            // set game frame time to crawl
            LocalDateTime currentDateTime = this.getCurrentDateTime();

            //LocalDateTime currentDateTime = liveGameStatDto.convertGameFrameDateTimeInLocalDateTime().plusMinutes(1L);

            // if last game frame time is before than current
            log.info("last-"+liveGameStatDto.convertGameFrameDateTimeInLocalDateTime());
            if(currentDateTime.isAfter(liveGameStatDto.convertGameFrameDateTimeInLocalDateTime())){
                // crawl live game stat data with game frame time
                Map<String, String> requestParameters = new HashMap<>();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                requestParameters.put("startingTime", currentDateTime.format(dateTimeFormatter)+".000Z");
                GameStatDto resultDto = Crawler.doGetObject(
                        this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                        new HashMap<>(),
                        requestParameters,
                        new TypeReference<>(){}
                );

                LiveGameStatDto newGameStatDto = resultDto.toLiveGameStatDto();

                // compare with old data
                LiveGameEventDto liveGameEventDto  = liveGameStatDto.compareTo(newGameStatDto);
                System.out.println(liveGameEventDto.getMessage());

                // save new game live stat on redis
                this.liveStatRedisMapper.saveLiveGameStat(gameId, newGameStatDto);
                return newGameStatDto;
            }
        }
        return liveGameStatDto;
    }

    public LocalDateTime getCurrentDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        log.info(localDateTime.toString());
        localDateTime = localDateTime.minusMinutes(2L);
        int sec = (localDateTime.getSecond() / 10) * 10;

        LocalDateTime newLocalDateTime = LocalDateTime.of(
                localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), sec, 0
        );
        log.info(newLocalDateTime.toString());
        return newLocalDateTime;
    }
}
