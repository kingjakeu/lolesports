package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import com.kingjakeu.lolesports.api.common.constant.LolSide;
import com.kingjakeu.lolesports.api.game.domain.Game;
import com.kingjakeu.lolesports.api.game.domain.TeamGameSummary;
import com.kingjakeu.lolesports.api.game.domain.TeamGameSummaryId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class TeamDto {
    private Long teamId;
    private String win;
    private Boolean firstBlood;
    private Boolean firstTower;
    private Boolean firstInhibitor;
    private Boolean firstBaron;
    private Boolean firstDragon;
    private Boolean firstRiftHerald;
    private Integer towerKills;
    private Integer inhibitorKills;
    private Integer baronKills;
    private Integer dragonKills;
    private Integer vilemawKills;
    private Integer riftHeraldKills;
    private Integer dominionVictoryScore;
    private ArrayList<BanDto> bans;

    public boolean isBlueTeam(){
        return this.teamId.equals(100L);
    }

    public boolean isRedTeam(){
        return this.teamId.equals(200L);
    }

    public boolean isWinTeam(){
        return CommonCode.WIN.codeEqualsTo(this.win);
    }

    public List<String> getBanChampionKeyList(){
        List<String> banChampionKeyList = new ArrayList<>();

        for(BanDto banDto : this.bans){
            banChampionKeyList.add(banDto.getChampionId().toString());
        }
        return banChampionKeyList;
    }

    public TeamGameSummary toTeamGameSummary(Game game){
        return TeamGameSummary.builder()
                .teamGameSummaryId(TeamGameSummaryId.builder()
                        .gameId(game.getId())
                        .side(this.isBlueTeam() ? LolSide.BLUE : LolSide.RED)
                        .build())
                .game(game)
                .win(CommonCode.WIN.codeEqualsTo(this.win))
                .firstBlood(this.firstBlood)
                .firstBaron(this.firstBaron)
                .firstDragon(this.firstDragon)
                .firstTower(this.firstTower)
                .firstInhibitor(this.firstInhibitor)
                .firstRiftHerald(this.firstRiftHerald)
                .towerKill(this.towerKills)
                .inhibitorKill(this.inhibitorKills)
                .baronKill(this.baronKills)
                .dragonKill(this.dragonKills)
                .riftHeraldKill(this.riftHeraldKills)
                .build();
    }
}
