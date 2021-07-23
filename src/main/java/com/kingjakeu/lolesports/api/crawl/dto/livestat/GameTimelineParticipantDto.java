package com.kingjakeu.lolesports.api.crawl.dto.livestat;

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
}
