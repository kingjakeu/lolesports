package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ParticipantDto {
    private Long participantId;
    private Long teamId;
    private Long championId;
    private Long spell1Id;
    private Long spell2Id;
    private StatDto stats;
    private TimelineDto timeline;
}
