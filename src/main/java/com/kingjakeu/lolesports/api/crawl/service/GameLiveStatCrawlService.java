package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingjakeu.lolesports.api.crawl.config.RiotFeedLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.*;
import com.kingjakeu.lolesports.api.live.domain.PlayerLiveStat;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventDto;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventMessageDto;
import com.kingjakeu.lolesports.api.live.dto.LiveGameStatDto;
import com.kingjakeu.lolesports.api.live.service.LiveStatRedisMapper;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GameLiveStatCrawlService {

    private final LiveStatRedisMapper liveStatRedisMapper;
    private final RiotFeedLolEsportsConfig feedLolEsportsConfig;

    public void test(String gameId) throws JsonProcessingException, InterruptedException {
        for (int i = 0; i < 30; i++) {
            System.out.println(i);
            this.crawlGameWindowFrame(gameId);
        }
    }

    public void crawlGameWindowFrame(String gameId) throws JsonProcessingException, InterruptedException {
        LiveGameStatDto liveGameStatDto = this.liveStatRedisMapper.getLiveGameStat(gameId);
        if(liveGameStatDto == null){
            liveGameStatDto = new LiveGameStatDto();
        }
        if(liveGameStatDto.isEmptyGameFrameDateTime() || true){
            ResponseEntity<String> responseEntity = Crawler.doGetResponseEntity(
                    this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                    new HashMap<>(),
                    new HashMap<>()
            );
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                GameStatDto resultDto = new ObjectMapper().readValue(responseEntity.getBody(), GameStatDto.class);
                liveGameStatDto = resultDto.toLiveGameStatDto();
            }
        }

        if(!liveGameStatDto.isEmptyGameFrameDateTime()){
            LocalDateTime currentDateTime = this.getCurrentDateTime().plusMinutes(1);
            //LocalDateTime currentDateTime = liveGameStatDto.convertGameFrameDateTimeInLocalDateTime().plusSeconds(30);
            if(currentDateTime.isAfter(liveGameStatDto.convertGameFrameDateTimeInLocalDateTime())){
                Map<String, String> requestParameters = new HashMap<>();
                requestParameters.put("startingTime", currentDateTime.toString()+".000Z");
                ResponseEntity<String> responseEntity = Crawler.doGetResponseEntity(
                        this.feedLolEsportsConfig.getUrl() + "/window/" + gameId,
                        new HashMap<>(),
                        requestParameters
                );

                GameStatDto resultDto = new ObjectMapper().readValue(responseEntity.getBody(), GameStatDto.class);
                LiveGameStatDto newGameStatDto = resultDto.toLiveGameStatDto();

                LiveGameEventDto liveGameEventDto  = liveGameStatDto.compareTo(newGameStatDto);
                LiveGameEventMessageDto messageDto = LiveGameEventMessageDto.builder()
                        .killPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getKillPlayer()))
                        .assistPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getAssistPlayer()))
                        .deathPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getDeathPlayer()))
                        .build();
                System.out.println(messageDto.getMessage());

                liveGameStatDto = newGameStatDto;
            }

        }
        this.liveStatRedisMapper.saveLiveGameStat(gameId, liveGameStatDto);
        //Thread.sleep(20000);
    }

    private LocalDateTime getCurrentDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        //localDateTime = localDateTime.minusMinutes(1L);
        int sec = (int)(Math.ceil((double) localDateTime.getSecond() / 10) * 10);
        sec = sec >= 60 ? 0 : sec;

        return LocalDateTime.of(
                localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), sec, 0
        );
    }

    private void checkDamageShareRank(String gameId){
        Set<ZSetOperations.TypedTuple<String>> damageRank = this.liveStatRedisMapper.getPlayerDamageShareWithScore(gameId);
        StringBuilder sb  = new StringBuilder();
        for(ZSetOperations.TypedTuple<String> rank : damageRank){
            sb.append(rank.getValue()).append("-").append(rank.getScore()).append("\n");
        }
        System.out.println(sb.toString());
    }

    private void checkEventHappened(String gameId, GameTimelineParticipantDto[] participantDtos){
        LiveGameEventDto liveGameEventDto = new LiveGameEventDto();
        for(GameTimelineParticipantDto participantDto : participantDtos){
            String playerId = String.valueOf(participantDto.getParticipantId());
            PlayerLiveStat playerLiveStat = this.liveStatRedisMapper.getPlayerStatHash(gameId, playerId);
            liveGameEventDto.addAll(participantDto.compareTo(playerLiveStat));
        }
        LiveGameEventMessageDto messageDto = LiveGameEventMessageDto.builder()
                .killPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getKillPlayer()))
                .assistPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getAssistPlayer()))
                .deathPlayer(this.liveStatRedisMapper.getPlayerHashList(gameId, liveGameEventDto.getDeathPlayer()))
                .build();
        System.out.println(messageDto.getMessage());
    }

    private void savePlayerStat(String gameId, GameTimelineParticipantDto[] participantDtos){
        for (GameTimelineParticipantDto participantDto : participantDtos){
            this.liveStatRedisMapper.savePlayerStatHash(
                    gameId,
                    String.valueOf(participantDto.getParticipantId()),
                    participantDto.toPlayerLiveStat()
            );
            this.liveStatRedisMapper.savePlayerDamageShare(
                    gameId,
                    String.valueOf(participantDto.getParticipantId()),
                    participantDto.getChampionDamageShare()
            );
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
