package com.kingjakeu.lolesports.api.crawl.dto.game;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class GameMatchDto {
    private GameMatchStrategyDto strategy;
    private ArrayList<GameParticipantTeamDto> teams;
    private ArrayList<GameDto> games;
}
