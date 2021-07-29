package com.kingjakeu.lolesports.api.live.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveGameStatDto implements Serializable {
    private String gameFrameDateTime;
    private Map<String, String> playerNameMap;
    private LiveTeamStatDto blueTeamStatDto;
    private LiveTeamStatDto redTeamStatDto;
    private Map<String, LivePlayerStatDto> bluePlayerStatDto;
    private Map<String, LivePlayerStatDto> redPlayerStatDto;

    @JsonIgnore
    public boolean isEmptyGameFrameDateTime(){
        return this.gameFrameDateTime == null;
    }

    public LocalDateTime convertGameFrameDateTimeInLocalDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(this.gameFrameDateTime, dateTimeFormatter);
    }
    public LiveGameEventDto compareTo(LiveGameStatDto liveGameStatDto){
        LiveGameEventDto liveGameEventDto = new LiveGameEventDto();

        for(Map.Entry<String, LivePlayerStatDto> bluePlayer : this.bluePlayerStatDto.entrySet()){
            LivePlayerStatDto playerStat = bluePlayer.getValue();
            LivePlayerStatDto oldPlayerStat = liveGameStatDto.getBluePlayerStatDto().get(bluePlayer.getKey());
            LivePlayerDiffKdaDto livePlayerDiffKdaDto = playerStat.compareKda(oldPlayerStat);
            if(livePlayerDiffKdaDto.isEventHappened()){
                liveGameEventDto.addEventPlayer(playerNameMap.get(bluePlayer.getKey()), livePlayerDiffKdaDto);
            }
        }
        return liveGameEventDto;
    }
}
