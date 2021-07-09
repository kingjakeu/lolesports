package com.kingjakeu.lolesports.api.crawl.dto.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingjakeu.lolesports.api.game.domain.Game;
import com.kingjakeu.lolesports.api.match.domain.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GameEventDto {
    private String id;
    private String type;
    private GameTournamentDto tournament;
    private GameLeagueDto league;

    @JsonProperty(value = "match")
    private GameMatchDto matchDto;
    private ArrayList<Object> streams;

    public List<Game> toGameEntities(Match match){
        List<Game> games = new ArrayList<>();
        for(GameDto gameDto : this.matchDto.getGames()){
            if(!gameDto.isUnneeded()){
                games.add(Game.builder()
                        .id(gameDto.getId())
                        .match(match)
                        .sequence(gameDto.getNumber().intValue())
                        .state(gameDto.getState())
                        .blueTeam(gameDto.getBlueTeam().toTeamEntity())
                        .redTeam(gameDto.getRedTeam().toTeamEntity())
                        .startTime(gameDto.getStartTime())
                        .startMillis(gameDto.getStartMillis())
                        .endMillis(gameDto.getEndMillis())
                        .build()
                );
            }
        }
        return games;
    }
}
