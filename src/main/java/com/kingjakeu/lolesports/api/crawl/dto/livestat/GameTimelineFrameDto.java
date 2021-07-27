package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameTimelineFrameDto {
    private String rfc460Timestamp;
    private GameTimelineParticipantDto[] participants;
}
