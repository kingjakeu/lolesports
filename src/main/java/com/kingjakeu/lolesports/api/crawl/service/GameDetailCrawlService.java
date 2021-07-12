package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.champion.dao.ChampionRepository;
import com.kingjakeu.lolesports.api.champion.domain.Champion;
import com.kingjakeu.lolesports.api.common.constant.LolRole;
import com.kingjakeu.lolesports.api.common.constant.LolSide;
import com.kingjakeu.lolesports.api.crawl.config.RiotMatchHistoryConfig;
import com.kingjakeu.lolesports.api.crawl.dto.matchhistory.MatchHistoryDto;
import com.kingjakeu.lolesports.api.crawl.dto.matchhistory.ParticipantDto;
import com.kingjakeu.lolesports.api.exception.ResourceNotFoundException;
import com.kingjakeu.lolesports.api.game.dao.GameRepository;
import com.kingjakeu.lolesports.api.game.dao.PlayerGameSummaryRepository;
import com.kingjakeu.lolesports.api.game.dao.TeamGameSummaryRepository;
import com.kingjakeu.lolesports.api.game.domain.Game;
import com.kingjakeu.lolesports.api.game.domain.PlayerGameSummary;
import com.kingjakeu.lolesports.api.game.domain.PlayerGameSummaryId;
import com.kingjakeu.lolesports.api.player.dao.PlayerRepository;
import com.kingjakeu.lolesports.api.player.domain.Player;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameDetailCrawlService {

    private final RiotMatchHistoryConfig matchHistoryConfig;
    private final GameRepository gameRepository;
    private final TeamGameSummaryRepository teamGameSummaryRepository;
    private final PlayerRepository playerRepository;
    private final ChampionRepository championRepository;
    private final PlayerGameSummaryRepository playerGameSummaryRepository;

    public void crawlGameDetailByGameId(String gameId){

        // Find Game Info
        Optional<Game> optionalGame = this.gameRepository.findById(gameId);
        if(optionalGame.isEmpty()) throw new ResourceNotFoundException(gameId);
        Game game = optionalGame.get();

        // Set Http header value
        Map<String, String> httpHeader = new HashMap<>();
        httpHeader.put("cookie", matchHistoryConfig.getCookie());

        // Get Game Detail Info via API
        MatchHistoryDto matchHistoryDto = Crawler.doGetObject(
                this.matchHistoryConfig.getConvertedUrl(game.getMatchHistoryUrl()),
                httpHeader,
                new HashMap<>(),
                new TypeReference<>() {}
        );

        // Save Team Game Summary
        this.teamGameSummaryRepository.saveAll(matchHistoryDto.toTeamGameSummaryList(game));

        // Save Player Game Summary
        int i = 0;
        List<PlayerGameSummary> playerGameSummaryList = new ArrayList<>();
        for(ParticipantDto participantDto : matchHistoryDto.getParticipants()){
            // find player info
            String summonerName = matchHistoryDto.findSummonerNameById(participantDto.getParticipantId());
            Optional<Player> optionalPlayer = this.playerRepository.findPlayerBySummonerName(summonerName);
            if(optionalPlayer.isEmpty()) throw new ResourceNotFoundException(summonerName);

            // find champion info
            String championId = participantDto.getChampionId().toString();
            Optional<Champion> optionalChampion = this.championRepository.findById(championId);
            if(optionalChampion.isEmpty()) throw new ResourceNotFoundException(championId);

            // Define Player Game Summary
            PlayerGameSummary playerGameSummary = participantDto.getStats().toPlayerGameSummaryEntity();
            playerGameSummary.setPlayerGameSummaryId(PlayerGameSummaryId.builder()
                    .gameId(game.getId())
                    .playerId(optionalPlayer.get().getId())
                    .build());
            playerGameSummary.setGame(game);
            playerGameSummary.setPlayer(optionalPlayer.get());
            playerGameSummary.setChampion(optionalChampion.get());
            playerGameSummary.setSide(i < 5 ? LolSide.BLUE : LolSide.RED);
            playerGameSummary.setRole(LolRole.findBySequence(i));

            playerGameSummaryList.add(playerGameSummary);
            i += 1;
        }
        this.playerGameSummaryRepository.saveAll(playerGameSummaryList);
    }

}
