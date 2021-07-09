package com.kingjakeu.lolesports.api.crawl.dto.game;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameParticipantTeamDto {
    private String id;
    private String name;
    private String code;
    private String image;
    private GameTeamResultDto result;
}
