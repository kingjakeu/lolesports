package com.kingjakeu.lolesports.api.crawl.dto.tournament;

import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TournamentLeagueDto {
    private ArrayList<TournamentDto> tournaments;

    public List<Tournament> toTournamentEntities(League league){
        List<Tournament> tournamentList = new ArrayList<>();
        for(TournamentDto tournamentDto : tournaments){
            tournamentList.add(tournamentDto.toTournamentEntity(league));
        }
        return tournamentList;
    }
}
