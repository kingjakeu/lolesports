package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.crawl.dto.LolEsportDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.league.LeagueDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.tournament.TournamentDataDto;
import com.kingjakeu.lolesports.api.league.dao.LeagueRepository;
import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.tournament.dao.TournamentRepository;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasicInfoCrawlService {

    private final RiotEsportsComponent riotEsportsComponent;
    private final LeagueRepository leagueRepository;
    private final TournamentRepository tournamentRepository;

    /**
     * Crawl League Info Data
     */
    public void crawlLeagueInfo(){
        // Get League List via esports API
        LolEsportDataDto<LeagueDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                "/getLeagues",
                this.riotEsportsComponent.createDefaultLolEsportParameters(),
                new TypeReference<>() {}
        );

        // Save League Info
        List<League> leagueList = resultDto.getData().toLeagueEntities();
        this.leagueRepository.saveAll(leagueList);
    }

    /**
     * Crawl Tournament Info By League ID
     * @param leagueId leagueId
     */
    public void crawlTournamentInfoByLeagueId(String leagueId){
        // Find League Info from DB
        League league = this.leagueRepository.getById(leagueId);

        // Put league id as requesting parameters
        Map<String, String> parameters = this.riotEsportsComponent.createDefaultLolEsportParameters();
        parameters.put("leagueId", leagueId);

        // Get Tournament List via esports API
        LolEsportDataDto<TournamentDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                "/getTournamentsForLeague",
                parameters,
               new TypeReference<>() {}
        );

        // Save Tournament Info
        if(!resultDto.getData().getLeagues().isEmpty()){
            List<Tournament> tournamentList = resultDto.getData().getLeagues().get(0).toTournamentEntities(league);
            this.tournamentRepository.saveAll(tournamentList);
        }
    }
}
