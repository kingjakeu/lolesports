package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TimelineDto {
    private Long participantId;
    private TimelineDetailDto creepsPerMinDeltas;
    private TimelineDetailDto xpPerMinDeltas;
    private TimelineDetailDto goldPerMinDeltas;
    private TimelineDetailDto damageTakenPerMinDeltas;
    private String role;
    private String lane;
}
