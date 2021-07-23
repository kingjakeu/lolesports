package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameFrameParticipantDto {
    private Integer participantId;
    private Integer totalGold;
    private Integer level;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer creepScore;
    private Integer currentHealth;
    private Integer maxHealth;
}
