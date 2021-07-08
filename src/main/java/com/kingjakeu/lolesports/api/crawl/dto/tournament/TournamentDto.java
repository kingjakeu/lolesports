package com.kingjakeu.lolesports.api.crawl.dto.tournament;

import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TournamentDto {
    private String id;
    private String slug;
    private String startDate;
    private String endDate;

    public Tournament toTournamentEntity(League league){
        return Tournament.builder()
                .id(this.id)
                .slug(this.slug)
                .league(league)
                .startDate(LocalDate.parse(this.startDate))
                .endDate(LocalDate.parse(this.endDate))
                .build();
    }
}
