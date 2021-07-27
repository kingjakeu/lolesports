package com.kingjakeu.lolesports.api.live.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLiveStat implements Serializable {
    private Integer level;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer totalGoldEarned;
    private Integer creepScore;
    private Double killParticipation;
    private Double championDamageShare;
    private Integer[] items;
}
