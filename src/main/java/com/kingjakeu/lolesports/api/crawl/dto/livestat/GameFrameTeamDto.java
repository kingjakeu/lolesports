package com.kingjakeu.lolesports.api.crawl.dto.livestat;


import com.kingjakeu.lolesports.api.live.dto.LivePlayerStatDto;
import com.kingjakeu.lolesports.api.live.dto.LiveTeamStatDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    public LiveTeamStatDto toLiveTeamStatDto(){
        return LiveTeamStatDto.builder()
                .kill(this.totalKills)
                .gold(this.totalGold)
                .tower(this.towers)
                .inhibitor(this.inhibitors)
                .baron(this.barons)
                .dragon(this.dragons)
                .build();
    }

    public Map<String, LivePlayerStatDto> toLivePlayerStatDtoMap(){
        Map<String, LivePlayerStatDto> livePlayerStatDtoMap = new HashMap<>();
        for(GameFrameParticipantDto participantDto : participants){
            livePlayerStatDtoMap.put(String.valueOf(participantDto.getParticipantId()), participantDto.toLivePlayerStatDto());
        }
        return livePlayerStatDtoMap;
    }
}
