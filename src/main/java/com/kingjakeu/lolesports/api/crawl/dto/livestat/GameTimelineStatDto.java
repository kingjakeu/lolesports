package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GameTimelineStatDto {
    private GameTimelineFrameDto[] frames;

    public RefinedEventDto compareTo(GameTimelineStatDto preDto){
        GameTimelineFrameDto preFrameDto = preDto.getFrames()[0];
        GameTimelineFrameDto currentFrameDto = this.frames[0];
        return currentFrameDto.compareTo(preFrameDto);
    }

    public LocalDateTime getFirstTimeFrame(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(this.getFrames()[0].getRfc460Timestamp(), dateTimeFormatter);
    }
}
