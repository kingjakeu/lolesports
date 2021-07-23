package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameTimelineFrameDto {
    private String rfc460Timestamp;
    private GameTimelineParticipantDto[] participants;

    public RefinedEventDto compareTo(GameTimelineFrameDto preFrameDto){
        RefinedEventDto refinedEventDto = new RefinedEventDto();
        for(int i = 0; i < participants.length; i++){
            GameTimelineParticipantDto preParticipant = preFrameDto.getParticipants()[i];
            GameTimelineParticipantDto currentParticipant = this.participants[i];

            if(preParticipant.getKills() < currentParticipant.getKills()){
                refinedEventDto.getKillPlayer().add(currentParticipant.getParticipantId());
            }
            if(preParticipant.getDeaths() < currentParticipant.getDeaths()){
                refinedEventDto.getDeathPlayer().add(currentParticipant.getParticipantId());
            }
            if(preParticipant.getAssists() < currentParticipant.getAssists()){
                refinedEventDto.getAssistPlayer().add(currentParticipant.getParticipantId());
            }
        }
        return refinedEventDto;
    }
}
