package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import com.kingjakeu.lolesports.api.live.dto.LivePlayerStatDto;
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

    public LivePlayerStatDto toLivePlayerStatDto(){
        return LivePlayerStatDto.builder()
                .kill(this.kills)
                .assist(this.assists)
                .death(this.deaths)
                .creepScore(this.creepScore)
                .gold(this.totalGold)
                .build();
    }
}
