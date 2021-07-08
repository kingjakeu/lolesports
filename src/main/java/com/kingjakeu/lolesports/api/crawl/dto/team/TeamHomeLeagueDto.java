package com.kingjakeu.lolesports.api.crawl.dto.team;

import com.kingjakeu.lolesports.api.league.domain.League;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamHomeLeagueDto {
    private String name;
    private String region;

    public boolean leagueEquals(League league){
        return league.getName().equals(this.name)
                && league.getRegion().equals(this.region);
    }
}
