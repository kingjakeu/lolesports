package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class GameStatDto {
    private String esportsGameId;
    private String esportsMatchId;
    private GameMetaDataDto gameMetadata;
    private GameFrameDto[] frames;

    public Map<Integer, String> getParticipantMap(){
        Map<Integer, String> participantMap = new HashMap<>();
        participantMap.putAll(this.gameMetadata.getBlueTeamMetadata().getParticipantMap());
        participantMap.putAll(this.getGameMetadata().getRedTeamMetadata().getParticipantMap());
        return participantMap;
    }
}
