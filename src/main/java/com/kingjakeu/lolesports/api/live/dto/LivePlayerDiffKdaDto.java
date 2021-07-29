package com.kingjakeu.lolesports.api.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LivePlayerDiffKdaDto {
    private Integer kill;
    private Integer assist;
    private Integer death;

    public boolean isEventHappened(){
        return this.kill > 0 || this.assist > 0 || this.death > 0;
    }
}
