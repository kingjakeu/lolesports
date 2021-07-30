package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import com.kingjakeu.lolesports.api.live.domain.PlayerLiveStat;
import com.kingjakeu.lolesports.api.live.dto.LiveGameEventDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameTimelineParticipantDto {
    private Integer participantId;
    private Integer level;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer totalGoldEarned;
    private Integer creepScore;
    private Double killParticipation;
    private Double championDamageShare;
    private Integer wardsPlaced;
    private Integer wardsDestroyed;
    private Integer attackDamage;
    private Integer abilityPower;
    private Double criticalChance;
    private Integer attackSpeed;
    private Integer lifeSteal;
    private Integer armor;
    private Integer magicResistance;
    private Double tenacity;
    private Integer[] items;
    private PerkMetaData perkMetadata;
    private String[] abilities;

    public PlayerLiveStat toPlayerLiveStat(){
        return PlayerLiveStat.builder()
                .level(this.level)
                .kills(this.kills)
                .deaths(this.deaths)
                .assists(this.assists)
                .totalGoldEarned(this.totalGoldEarned)
                .creepScore(this.creepScore)
                .killParticipation(this.killParticipation)
                .championDamageShare(this.championDamageShare)
                .items(this.items)
                .build();
    }
}
