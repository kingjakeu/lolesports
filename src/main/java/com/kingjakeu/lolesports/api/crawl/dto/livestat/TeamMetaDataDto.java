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

    public Map<String, String> getParticipantMap(){
        Map<String, String> participantMap = new HashMap<>();
        for(ParticipantMetaDataDto metaDataDto : this.participantMetadata){
            participantMap.put(String.valueOf(metaDataDto.getParticipantId()), metaDataDto.getSummonerName());
        }
        return participantMap;
    }
}
