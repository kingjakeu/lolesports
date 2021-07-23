package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantMetaDataDto {
    private Integer participantId;
    private String esportsPlayerId;
    private String summonerName;
    private String championId;
    private String role;
}
