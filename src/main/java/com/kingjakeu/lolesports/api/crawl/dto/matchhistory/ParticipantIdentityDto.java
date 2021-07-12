package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class ParticipantIdentityDto {
    private Long participantId;
    private ParticipantPlayerDto player;
}
