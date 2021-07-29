package com.kingjakeu.lolesports.api.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivePlayerStatDto implements Serializable {
    private Integer kill;
    private Integer assist;
    private Integer death;
    private Integer creepScore;
    private Integer gold;

    public LivePlayerDiffKdaDto compareKda(LivePlayerStatDto livePlayerStatDto){
        return LivePlayerDiffKdaDto.builder()
                .kill(this.kill - livePlayerStatDto.getKill())
                .assist(this.assist - livePlayerStatDto.getAssist())
                .death(this.death - livePlayerStatDto.getDeath())
                .build();
    }
}
