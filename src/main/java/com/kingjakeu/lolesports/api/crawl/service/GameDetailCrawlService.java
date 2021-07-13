package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.ban.dao.BanHistoryRepository;
import com.kingjakeu.lolesports.api.ban.domain.BanHistory;
import com.kingjakeu.lolesports.api.ban.domain.BanHistoryId;
import com.kingjakeu.lolesports.api.champion.dao.ChampionRepository;
import com.kingjakeu.lolesports.api.champion.domain.Champion;
import com.kingjakeu.lolesports.api.common.constant.LolRole;
import com.kingjakeu.lolesports.api.common.constant.LolSide;
import com.kingjakeu.lolesports.api.crawl.config.RiotMatchHistoryConfig;
import com.kingjakeu.lolesports.api.crawl.dto.matchhistory.MatchHistoryDto;
import com.kingjakeu.lolesports.api.crawl.dto.matchhistory.ParticipantDto;
import com.kingjakeu.lolesports.api.crawl.dto.matchhistory.TeamDto;
import com.kingjakeu.lolesports.api.exception.ResourceNotFoundException;
import com.kingjakeu.lolesports.api.game.dao.*;
import com.kingjakeu.lolesports.api.game.domain.*;
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
    private final BanHistoryRepository banHistoryRepository;
    private final PlayerItemHistoryRepository playerItemHistoryRepository;
    private final PlayerRuneHistoryRepository playerRuneHistoryRepository;

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
        this.savePlayerGameSummary(matchHistoryDto, game);

        // Save Ban History
        this.saveBanHistory(game, matchHistoryDto.getBlueTeamDto());
        this.saveBanHistory(game, matchHistoryDto.getRedTeamDto());
    }

    /**
     * Save Player Game Summary
     * @param matchHistoryDto match Info
     * @param game game
     */
    private void savePlayerGameSummary(MatchHistoryDto matchHistoryDto, Game game){
        int i = 0;
        List<PlayerGameSummary> playerGameSummaryList = new ArrayList<>();
        List<PlayerRuneHistory> playerRuneHistoryList = new ArrayList<>();
        List<PlayerItemHistory> playerItemHistoryList = new ArrayList<>();

        for(ParticipantDto participantDto : matchHistoryDto.getParticipants()){
            // find player info
            String summonerName = matchHistoryDto.findSummonerNameById(participantDto.getParticipantId());
            Optional<Player> optionalPlayer = this.playerRepository.findPlayerBySummonerName(summonerName);
            if(optionalPlayer.isEmpty()) throw new ResourceNotFoundException(summonerName);
            final Player player = optionalPlayer.get();

            // Define Player Game Summary
            PlayerGameSummary playerGameSummary = participantDto.getStats().toPlayerGameSummaryEntity();
            playerGameSummary.setPlayerGameSummaryId(PlayerGameSummaryId.builder()
                    .gameId(game.getId())
                    .playerId(player.getId())
                    .build());
            playerGameSummary.setGame(game);
            playerGameSummary.setPlayer(player);
            playerGameSummary.setChampion(this.findChampionById(participantDto.getChampionId().toString()));
            playerGameSummary.setSide(i < 5 ? LolSide.BLUE : LolSide.RED);
            playerGameSummary.setRole(LolRole.findBySequence(i));

            playerGameSummaryList.add(playerGameSummary);
            i += 1;

            // Add item & rune History by Player
            playerRuneHistoryList.add(participantDto.getStats().toPlayerRuneHistory(game, player));
            playerItemHistoryList.add(participantDto.getStats().toPlayerItemHistory(game, player));
        }
        this.playerGameSummaryRepository.saveAll(playerGameSummaryList);
        this.playerItemHistoryRepository.saveAll(playerItemHistoryList);
        this.playerRuneHistoryRepository.saveAll(playerRuneHistoryList);
    }

    /**
     * Save Ban History
     * @param game game
     * @param teamDto target Team
     */
    private void saveBanHistory(Game game, TeamDto teamDto) {
        List<String> banChampionKeyList = teamDto.getBanChampionKeyList();
        List<BanHistory> banHistoryList = new ArrayList<>();
        int banTurn = 1;
        for(String champKey : banChampionKeyList){
            banHistoryList.add(BanHistory.builder()
                    .banHistoryId(BanHistoryId.builder()
                            .gameId(game.getId())
                            .side(teamDto.isBlueTeam() ? LolSide.BLUE : LolSide.RED)
                            .banTurn(banTurn++) // increase by seq
                            .build())
                    .game(game)
                    .bannedChampion(this.findChampionById(champKey))
                    .patchVersion(game.getPatchVersion())
                    .build()
            );
        }
        this.banHistoryRepository.saveAll(banHistoryList);
    }

    /**
     * find champion info
     * @param championId champion Id
     * @return Champion info
     */
    private Champion findChampionById(String championId){
        Optional<Champion> optionalChampion = this.championRepository.findById(championId);
        if(optionalChampion.isEmpty()) throw new ResourceNotFoundException(championId);
        return optionalChampion.get();
    }

}
