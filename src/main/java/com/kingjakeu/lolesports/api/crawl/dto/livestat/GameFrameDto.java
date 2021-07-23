package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameFrameDto {
    private String rfc460Timestamp;
    private String gameState;
    private GameFrameTeamDto blueTeam;
    private GameFrameTeamDto redTeam;
}
