package com.kingjakeu.lolesports.api.common.constant;

import lombok.Getter;

@Getter
public enum RiotEsportsApi {

    LEAGUE_INFO("/getLeagues"),
    TOURNAMENT_INFO("/getTournamentsForLeague"),
    LEAGUE_SCHEDULE_INFO("/getSchedule"),
    TEAM_INFO("/getTeams"),


    ;
    private String uri;
    RiotEsportsApi(String uri){
        this.uri = uri;
    }
}
