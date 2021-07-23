package com.kingjakeu.lolesports.api.crawl.dto.livestat;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameFrameTeamDto {
    private Integer totalGold;
    private Integer inhibitors;
    private Integer towers;
    private Integer barons;
    private Integer totalKills;
    private String[] dragons;
    private GameFrameParticipantDto[] participants;
}
