package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameTimelineStatDto {
    private GameTimelineFrameDto[] frames;

    public RefinedEventDto compareTo(GameTimelineStatDto preDto){
        GameTimelineFrameDto preFrameDto = preDto.getFrames()[0];
        GameTimelineFrameDto currentFrameDto = this.frames[0];
        return currentFrameDto.compareTo(preFrameDto);
    }
}
