package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingjakeu.lolesports.api.crawl.config.RiotFeedLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.livestat.*;
import com.kingjakeu.lolesports.api.live.domain.PlayerLiveStat;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventDto;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventMessageDto;
import com.kingjakeu.lolesports.api.live.service.LiveStatRedisMapper;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameLiveStatCrawlService {

    private final LiveStatRedisMapper liveStatRedisMapper;
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
            this.liveStatRedisMapper.savePlayerHash(gameId, resultDto.getParticipantMap());

            LocalDateTime gameDateTime = resultDto.getFirstTimeFrame();
            GameTimelineStatDto gameTimelineStatDto = this.crawlGameDetailFrame(gameId, gameDateTime);
            this.savePlayerStat(gameId, gameTimelineStatDto.getFirstGameTimelineFrameDto().getParticipants());

            // do pub
            for(int i=0; i < 100; i++){
                System.out.println(i);
                gameDateTime = gameDateTime.plusMinutes(1L);

                gameTimelineStatDto = this.crawlGameDetailFrame(gameId, gameDateTime);

                if(gameTimelineStatDto == null) break;

                this.checkEventHappened(gameId, gameTimelineStatDto.getFirstGameTimelineFrameDto().getParticipants());
                this.savePlayerStat(gameId, gameTimelineStatDto.getFirstGameTimelineFrameDto().getParticipants());
            }
        }
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
