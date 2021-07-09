package com.kingjakeu.lolesports.api.crawl.dto.game;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class GameDto {
    private Long number;
    private String id;
    private String state;
    private ArrayList<GameTeamDto> teams;
    private ArrayList<GameVodDto> vods;

    public GameTeamDto getBlueTeam(){
        for(GameTeamDto gameTeamDto : teams){
            if(gameTeamDto.getSide().equals("blue")){
                return gameTeamDto;
            }
        }
        return null;
    }

    public GameTeamDto getRedTeam(){
        for(GameTeamDto gameTeamDto : teams){
            if(gameTeamDto.getSide().equals("red")){
                return gameTeamDto;
            }
        }
        return null;
    }

    public LocalDateTime getStartTime(){
        if(vods.isEmpty()) return null;
        return vods.get(0).getFirstFrameTime() == null ? null : ZonedDateTime.parse(vods.get(0).getFirstFrameTime()).toLocalDateTime();
    }

    public Long getStartMillis(){
        return vods.isEmpty() ? null : vods.get(0).getStartMillis();
    }

    public Long getEndMillis(){
        return vods.isEmpty() ? null : vods.get(0).getEndMillis();
    }

    public boolean isUnneeded(){
        return CommonCode.STATE_UNNEEDED.codeEqualsTo(this.state);
    }
}
