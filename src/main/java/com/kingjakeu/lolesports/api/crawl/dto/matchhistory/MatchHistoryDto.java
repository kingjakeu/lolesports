package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import com.kingjakeu.lolesports.api.game.domain.Game;
import com.kingjakeu.lolesports.api.game.domain.PlayerGameSummary;
import com.kingjakeu.lolesports.api.game.domain.TeamGameSummary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Getter
@NoArgsConstructor
@ToString
public class MatchHistoryDto {
    private Long gameId;
    private String platformId;
    private Long gameCreation;
    private Long gameDuration;
    private Long queueId;
    private Long mapId;
    private Long seasonId;
    private String gameVersion;
    private String gameMode;
    private String gameType;
    private ArrayList<TeamDto> teams;
    private ArrayList<ParticipantDto> participants;
    private ArrayList<ParticipantIdentityDto> participantIdentities;

    private Map<Long, String> participantIdMap;

    public List<TeamGameSummary> toTeamGameSummaryList(Game game){
        return Arrays.asList(
                this.getBlueTeamDto().toTeamGameSummary(game),
                this.getRedTeamDto().toTeamGameSummary(game)
        );
    }

    public TeamDto getBlueTeamDto(){
        for (TeamDto teamDto : this.teams){
            if(teamDto.isBlueTeam()) return teamDto;
        }
        return null;
    }

    public TeamDto getRedTeamDto(){
        for(TeamDto teamDto : this.teams){
            if(teamDto.isRedTeam()) return teamDto;
        }
        return null;
    }

    public String findSummonerNameById(Long id){
        if(this.participantIdMap == null){
            this.participantIdMap = new HashMap<>();
            for(ParticipantIdentityDto identityDto : this.participantIdentities){
                this.participantIdMap.put(identityDto.getParticipantId(), identityDto.getPlayer().getRefinedSummonerName());
            }
        }
        return this.participantIdMap.get(id);
    }
}
