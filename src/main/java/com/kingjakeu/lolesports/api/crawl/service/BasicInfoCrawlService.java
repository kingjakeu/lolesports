package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.common.constant.RiotEsportsApi;
import com.kingjakeu.lolesports.api.crawl.dto.LolEsportDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.league.LeagueDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleEventDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleTeamDto;
import com.kingjakeu.lolesports.api.crawl.dto.tournament.TournamentDataDto;
import com.kingjakeu.lolesports.api.league.dao.LeagueRepository;
import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.match.dao.MatchRepository;
import com.kingjakeu.lolesports.api.match.domain.Match;
import com.kingjakeu.lolesports.api.team.dao.TeamRepository;
import com.kingjakeu.lolesports.api.tournament.dao.TournamentRepository;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicInfoCrawlService {

    private final RiotEsportsComponent riotEsportsComponent;
    private final LeagueRepository leagueRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

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

    /**
     * Crawl Match Info By Tournament ID
     * @param tournamentId tournament ID
     */
    public void crawlMatchInfoByTournamentId(String tournamentId){
        // Find Tournament Info from DB
        Optional<Tournament> optionalTournament = this.tournamentRepository.findById(tournamentId);
        if(optionalTournament.isEmpty()) throw new RuntimeException();
        Tournament tournament = optionalTournament.get();

        // Put league id as requesting parameters
        Map<String, String> parameters = this.riotEsportsComponent.createDefaultLolEsportParameters();
        parameters.put("leagueId", tournament.getLeague().getId());

        // Get Schedule Info via esports API
        ScheduleDto scheduleDto = this.crawlLeagueScheduleEvent(parameters);
        List<Match> matchList = new LinkedList<>(this.refineScheduledMatch(scheduleDto.getEvents(), tournament));

        // Save Match List
        while(scheduleDto.getPages().getOlder() != null){
            parameters.put("pageToken", scheduleDto.getPages().getOlder());
            // Get older Schedule Info via esports API
            scheduleDto = this.crawlLeagueScheduleEvent(parameters);
            matchList.addAll(this.refineScheduledMatch(scheduleDto.getEvents(), tournament));
        }
        this.matchRepository.saveAll(matchList);
    }

    /**
     * Crawl Scheduled Match events
     * @param parameters request parameters
     * @return schedule
     */
    private ScheduleDto crawlLeagueScheduleEvent(Map<String, String> parameters){
        // Get Schedule Info via esports API
        LolEsportDataDto<ScheduleDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                RiotEsportsApi.LEAGUE_SCHEDULE_INFO.getUri(),
                parameters,
                new TypeReference<>() {}
        );
        return resultDto.getData().getSchedule();
    }

    /**
     * Refine schedule info to Match Info
     * @param scheduleEventDtoList schedule List
     * @param tournament requesting tournament
     * @return match info
     */
    private List<Match> refineScheduledMatch(List<ScheduleEventDto> scheduleEventDtoList, Tournament tournament){
        List<Match> matchList = new LinkedList<>();
        // Save Match List
        for(ScheduleEventDto scheduleEventDto : scheduleEventDtoList){
            // Only not in progress tournament matches
            if(scheduleEventDto.isNotInProgress()
                    && scheduleEventDto.isStartDateBetween(tournament.getStartDate(), tournament.getEndDate())){
                // Match Info
                Match match = scheduleEventDto.toMatchEntity();
                match.setTournament(tournament);

                // participated teams
                List<ScheduleTeamDto> scheduleTeamDtoList = scheduleEventDto.getMatch().getTeams();
                match.setTeam1(this.teamRepository.getTeamByCode(scheduleTeamDtoList.get(0).getCode()));
                match.setTeam2(this.teamRepository.getTeamByCode(scheduleTeamDtoList.get(1).getCode()));
                matchList.add(match);
            }
        }
        return matchList;
    }

}
