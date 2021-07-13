package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import com.kingjakeu.lolesports.api.game.domain.*;
import com.kingjakeu.lolesports.api.player.domain.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class StatDto {
    private Long participantId;
    private Boolean win;
    private Long item0;
    private Long item1;
    private Long item2;
    private Long item3;
    private Long item4;
    private Long item5;
    private Long item6;
    private Long kills;
    private Long deaths;
    private Long assists;
    private Long largestKillingSpree;
    private Long largestMultiKill;
    private Long killingSprees;
    private Long longestTimeSpentLiving;
    private Long doubleKills;
    private Long tripleKills;
    private Long quadraKills;
    private Long pentaKills;
    private Long unrealKills;
    private Long totalDamageDealt;
    private Long magicDamageDealt;
    private Long physicalDamageDealt;
    private Long trueDamageDealt;
    private Long largestCriticalStrike;
    private Long totalDamageDealtToChampions;
    private Long magicDamageDealtToChampions;
    private Long physicalDamageDealtToChampions;
    private Long trueDamageDealtToChampions;
    private Long totalHeal;
    private Long totalUnitsHealed;
    private Long damageSelfMitigated;
    private Long damageDealtToObjectives;
    private Long damageDealtToTurrets;
    private Long visionScore;
    private Long timeCCingOthers;
    private Long totalDamageTaken;
    private Long magicalDamageTaken;
    private Long physicalDamageTaken;
    private Long trueDamageTaken;
    private Long goldEarned;
    private Long goldSpent;
    private Long turretKills;
    private Long inhibitorKills;
    private Long totalMinionsKilled;
    private Long neutralMinionsKilled;
    private Long neutralMinionsKilledTeamJungle;
    private Long neutralMinionsKilledEnemyJungle;
    private Long totalTimeCrowdControlDealt;
    private Long champLevel;
    private Long visionWardsBoughtInGame;
    private Long sightWardsBoughtInGame;
    private Long wardsPlaced;
    private Long wardsKilled;
    private Boolean firstBloodKill;
    private Boolean firstBloodAssist;
    private Boolean firstTowerKill;
    private Boolean firstTowerAssist;
    private Boolean firstInhibitorKill;
    private Boolean firstInhibitorAssist;
    private Long combatPlayerScore;
    private Long objectivePlayerScore;
    private Long totalPlayerScore;
    private Long totalScoreRank;
    private Long playerScore0;
    private Long playerScore1;
    private Long playerScore2;
    private Long playerScore3;
    private Long playerScore4;
    private Long playerScore5;
    private Long playerScore6;
    private Long playerScore7;
    private Long playerScore8;
    private Long playerScore9;
    private Long perk0;
    private Long perk0Var1;
    private Long perk0Var2;
    private Long perk0Var3;
    private Long perk1;
    private Long perk1Var1;
    private Long perk1Var2;
    private Long perk1Var3;
    private Long perk2;
    private Long perk2Var1;
    private Long perk2Var2;
    private Long perk2Var3;
    private Long perk3;
    private Long perk3Var1;
    private Long perk3Var2;
    private Long perk3Var3;
    private Long perk4;
    private Long perk4Var1;
    private Long perk4Var2;
    private Long perk4Var3;
    private Long perk5;
    private Long perk5Var1;
    private Long perk5Var2;
    private Long perk5Var3;
    private Long perkPrimaryStyle;
    private Long perkSubStyle;
    private Long statPerk0;
    private Long statPerk1;
    private Long statPerk2;

    public PlayerGameSummary toPlayerGameSummaryEntity(){
        return PlayerGameSummary.builder()
                .champLevel(this.champLevel)
                .kill(this.kills)
                .death(this.deaths)
                .assist(this.assists)
                .totalDamageDealt(this.totalDamageDealt)
                .totalDamageDealtToChampion(this.totalDamageDealtToChampions)
                .totalDamageTaken(this.totalDamageTaken)
                .totalHeal(this.totalHeal)
                .damageDealtToObject(this.damageDealtToObjectives)
                .damageDealtToTurret(this.damageDealtToTurrets)
                .totalGoldEarned(this.goldEarned)
                .totalMinionKilled(this.totalMinionsKilled)
                .turretKill(this.turretKills)
                .totalJungleMinionKilled(this.neutralMinionsKilled)
                .teamJungleMinionKilled(this.neutralMinionsKilledTeamJungle)
                .enemyJungleMinionKilled(this.neutralMinionsKilledEnemyJungle)
                .visionWardBuyCount(this.visionWardsBoughtInGame)
                .sightWardBuyCount(this.sightWardsBoughtInGame)
                .wardPlaced(this.wardsPlaced)
                .wardKilled(this.wardsKilled)
                .visionScore(this.visionScore)
                .build();
    }

    public PlayerItemHistory toPlayerItemHistory(Game game, Player player){
        return PlayerItemHistory.builder()
                .playerItemHistoryId(PlayerItemHistoryId.builder()
                        .gameId(game.getId())
                        .playerId(player.getId())
                        .build())
                .game(game)
                .player(player)
                .item0(this.item0.toString())
                .item1(this.item1.toString())
                .item2(this.item2.toString())
                .item3(this.item3.toString())
                .item4(this.item4.toString())
                .item5(this.item5.toString())
                .item6(this.item6.toString())
                .build();
    }

    public PlayerRuneHistory toPlayerRuneHistory(Game game, Player player){
        return PlayerRuneHistory.builder()
                .playerRuneHistoryId(PlayerRuneHistoryId.builder().build())
                .game(game)
                .player(player)
                .primaryRuneStyle(this.perkPrimaryStyle.toString())
                .subRuneStyle(this.perkSubStyle.toString())
                .runePick0(this.perk0.toString())
                .runePick1(this.perk1.toString())
                .runePick2(this.perk2.toString())
                .runePick3(this.perk3.toString())
                .runePick4(this.perk4.toString())
                .runePick5(this.perk5.toString())
                .statRune0(this.statPerk0.toString())
                .statRune1(this.statPerk1.toString())
                .statRune2(this.statPerk2.toString())
                .build();
    }
}
