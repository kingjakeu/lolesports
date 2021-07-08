package com.kingjakeu.lolesports.api.crawl.dto.league;

import com.kingjakeu.lolesports.api.league.domain.League;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class LeagueDataDto {
    private ArrayList<LeagueDto> leagues;

    public List<League> toLeagueEntities(){
        List<League> leagueList = new ArrayList<>();
        for(LeagueDto leagueDto : leagues){
            leagueList.add(leagueDto.toLeagueEntity());
        }
        return leagueList;
    }
}
