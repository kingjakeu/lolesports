package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.common.constant.RiotEsportsApi;
import com.kingjakeu.lolesports.api.crawl.dto.LolEsportDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.team.TeamDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.team.TeamDto;
import com.kingjakeu.lolesports.api.league.dao.LeagueRepository;
import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.team.dao.TeamRepository;
import com.kingjakeu.lolesports.api.team.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantInfoCrawlService {

    private final RiotEsportsComponent riotEsportsComponent;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    /**
     * Crawl Team Info in the League
     * @param leagueId league id
     */
    public void crawlTeamInfoByLeagueId(String leagueId){
        // Find League Info
        Optional<League> optionalLeague = this.leagueRepository.findById(leagueId);
        if(optionalLeague.isEmpty()) throw new RuntimeException("error");

        League league = optionalLeague.get();

        // Get League List via esports API
        LolEsportDataDto<TeamDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                RiotEsportsApi.TEAM_INFO.getUri(),
                this.riotEsportsComponent.createDefaultLolEsportParameters(),
                new TypeReference<>() {}
        );
        List<TeamDto> teamDtoList = resultDto.getData().getTeams();

        // Save Team Info List
        List<Team> teamList = new ArrayList<>();
        for(TeamDto teamDto : teamDtoList){
            // Only currently active team in league
            if(teamDto.isActiveTeam()
                    && teamDto.leagueEquals(league)){
                teamList.add(teamDto.toTeamEntity(league));
            }
        }
        this.teamRepository.saveAll(teamList);
    }
}
