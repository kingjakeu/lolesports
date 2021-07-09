package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.common.constant.RiotEsportsApi;
import com.kingjakeu.lolesports.api.crawl.config.GamePediaConfig;
import com.kingjakeu.lolesports.api.crawl.dto.LolEsportDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.game.GameDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.game.GameEventDto;
import com.kingjakeu.lolesports.api.crawl.dto.league.LeagueDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleEventDto;
import com.kingjakeu.lolesports.api.crawl.dto.schedule.ScheduleTeamDto;
import com.kingjakeu.lolesports.api.crawl.dto.tournament.TournamentDataDto;
import com.kingjakeu.lolesports.api.exception.ResourceNotFoundException;
import com.kingjakeu.lolesports.api.game.dao.GameRepository;
import com.kingjakeu.lolesports.api.game.domain.Game;
import com.kingjakeu.lolesports.api.league.dao.LeagueRepository;
import com.kingjakeu.lolesports.api.league.domain.League;
import com.kingjakeu.lolesports.api.match.dao.MatchRepository;
import com.kingjakeu.lolesports.api.match.domain.Match;
import com.kingjakeu.lolesports.api.team.dao.TeamRepository;
import com.kingjakeu.lolesports.api.tournament.dao.TournamentRepository;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchInfoCrawlService {

    private final RiotEsportsComponent riotEsportsComponent;
    private final GamePediaConfig gamePediaConfig;

    private final LeagueRepository leagueRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final GameRepository gameRepository;

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

    /**
     * Crawl Game Info By Match
     * @param matchId matchId
     */
    public void crawlGameInfoByMatchId(String matchId){
        // Find Match Info
        Optional<Match> optionalMatch = this.matchRepository.findById(matchId);
        if(optionalMatch.isEmpty()) throw new ResourceNotFoundException(matchId);
        Match match = optionalMatch.get();

        // Set requesting parameter
        Map<String, String> parameters = this.riotEsportsComponent.createDefaultLolEsportParameters();
        parameters.put("id", matchId);

        // Crawl Game Info via esports API
        LolEsportDataDto<GameDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                RiotEsportsApi.GAME_INFO.getUri(),
                parameters,
                new TypeReference<>() {}
        );

        // Save Game Infos
        GameEventDto gameEventDto = resultDto.getData().getEvent();
        this.gameRepository.saveAll(gameEventDto.toGameEntities(match));
    }

    /**
     * Crawl MatchHistory Link for Game Details By Game ID
     * @param gameId gameId
     */
    public void crawlMatchHistoryLinkByGameId(String gameId){
        // Find Game Info
        Optional<Game> optionalGame = this.gameRepository.eagerFindGameById(gameId);
        if(optionalGame.isEmpty()) throw new ResourceNotFoundException(gameId);
        Game game = optionalGame.get();

        // Crawl Gamepedia page
        Document document = Crawler.doGetDocument(
                this.gamePediaConfig.getMatchHistoryUrl(
                        game
                        .getMatch()
                        .getTournament()
                        .getLeague()
                        .getName())
        );
        // Get Match History Link from doc
        String matchHistoryLink = this.parseMatchHistoryLink(document, game);

        // Save Match history Link
        game.setMatchHistoryUrl(matchHistoryLink);
        this.gameRepository.save(game);
    }

    /**
     * Parse Match History Link from GamePedia page
     * @param document page doc
     * @param game game
     * @return matchHistory Link or null
     */
    private String parseMatchHistoryLink(Document document, Game game){
        Elements rowElements = document.getElementsByClass("mhgame-red multirow-highlighter");
        rowElements.addAll(document.getElementsByClass("mhgame-blue multirow-highlighter"));

        for(Element row : rowElements){
            String gameDate = row.getElementsByClass("mhgame-result").get(0).text();

            Elements cells = row.getElementsByTag("a");

            String blueTeamLinkId = cells.get(1).attr("data-to-id");
            String redTeamLinkId = cells.get(2).attr("data-to-id");

            String matchHistoryLink = cells.get(15).attr("href");
            if(game.getMatch().startDateEqualsTo(LocalDate.parse(gameDate))){
                if(game.getBlueTeam().isUrlNameEquals(blueTeamLinkId) && game.getRedTeam().isUrlNameEquals(redTeamLinkId)){
                    return matchHistoryLink;
                }
            }
        }
        return null;
    }
}
