package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import com.kingjakeu.lolesports.api.live.dto.LiveGameStatDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class GameStatDto {
    private String esportsGameId;
    private String esportsMatchId;
    private GameMetaDataDto gameMetadata;
    private GameFrameDto[] frames;

    public LiveGameStatDto toLiveGameStatDto(){
        GameFrameDto gameFrameDto = frames[0];
        return LiveGameStatDto.builder()
                .gameFrameDateTime(this.getFirstTimeFrame().toString())
                .playerNameMap(this.getParticipantMap())
                .blueTeamStatDto(gameFrameDto.getBlueTeam().toLiveTeamStatDto())
                .redTeamStatDto(gameFrameDto.getRedTeam().toLiveTeamStatDto())
                .bluePlayerStatDto(gameFrameDto.getBlueTeam().toLivePlayerStatDtoMap())
                .redPlayerStatDto(gameFrameDto.getRedTeam().toLivePlayerStatDtoMap())
                .build();
    }

    public Map<String, String> getParticipantMap(){
        Map<String, String> participantMap = new HashMap<>();
        participantMap.putAll(this.gameMetadata.getBlueTeamMetadata().getParticipantMap());
        participantMap.putAll(this.gameMetadata.getRedTeamMetadata().getParticipantMap());
        return participantMap;
    }

    public LocalDateTime getFirstTimeFrame(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime localDateTime = LocalDateTime.parse(this.getFrames()[0].getRfc460Timestamp(), dateTimeFormatter);
        int sec = (int)(Math.ceil((double) localDateTime.getSecond() / 10) * 10);

        return LocalDateTime.of(
                localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), sec, 0
        );
    }
}
