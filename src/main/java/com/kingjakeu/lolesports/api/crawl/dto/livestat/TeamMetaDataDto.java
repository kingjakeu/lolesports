package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class TeamMetaDataDto {
    private String esportsTeamId;
    private ParticipantMetaDataDto[] participantMetadata;

    public Map<Integer, String> getParticipantMap(){
        Map<Integer, String> participantMap = new HashMap<>();
        for(ParticipantMetaDataDto metaDataDto : this.participantMetadata){
            participantMap.put(metaDataDto.getParticipantId(), metaDataDto.getSummonerName());
        }
        return participantMap;
    }
}
