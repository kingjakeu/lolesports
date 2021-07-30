package com.kingjakeu.lolesports.api.live.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
        //Text '2021-07-30T08:21' could not be parsed at index 16
        return LocalDateTime.parse(this.gameFrameDateTime, dateTimeFormatter);
    }

    public LiveGameEventDto compareTo(LiveGameStatDto liveGameStatDto){
        LiveGameEventDto liveGameEventDto = new LiveGameEventDto();
        liveGameEventDto.setDragon(this.checkDragonEvent(liveGameStatDto));
        liveGameEventDto.setBaron(this.checkBaronEvent(liveGameStatDto));
        liveGameEventDto.setTower(this.checkTowerEvent(liveGameStatDto));
        liveGameEventDto.setInhibitor(this.checkInhibitorEvent(liveGameStatDto));
        liveGameEventDto.setGoldDiff(this.checkGoldDiff(liveGameStatDto));

        for(Map.Entry<String, LivePlayerStatDto> bluePlayer : this.bluePlayerStatDto.entrySet()){
            LivePlayerStatDto oldPlayerStat = bluePlayer.getValue();
            LivePlayerStatDto newPlayerStat = liveGameStatDto.getBluePlayerStatDto().get(bluePlayer.getKey());
            LivePlayerDiffKdaDto livePlayerDiffKdaDto = newPlayerStat.compareKda(oldPlayerStat);
            if(livePlayerDiffKdaDto.isEventHappened()){
                liveGameEventDto.addBlueEventPlayer(this.playerNameMap.get(bluePlayer.getKey()), livePlayerDiffKdaDto);
            }
        }

        for(Map.Entry<String, LivePlayerStatDto> redPlayer : this.redPlayerStatDto.entrySet()){
            LivePlayerStatDto oldPlayerStat = redPlayer.getValue();
            LivePlayerStatDto newPlayerStat = liveGameStatDto.getRedPlayerStatDto().get(redPlayer.getKey());
            LivePlayerDiffKdaDto livePlayerDiffKdaDto = newPlayerStat.compareKda(oldPlayerStat);
            if(livePlayerDiffKdaDto.isEventHappened()){
                liveGameEventDto.addRedEventPlayer(this.playerNameMap.get(redPlayer.getKey()), livePlayerDiffKdaDto);
            }
        }
        return liveGameEventDto;
    }

    private Map<String, String> checkDragonEvent(LiveGameStatDto liveGameStatDto){
        Map<String, String> dragon = new HashMap<>();
        int blueLength = liveGameStatDto.getBlueTeamStatDto().getDragon().length;
        if(blueLength> this.blueTeamStatDto.getDragon().length){
            dragon.put(CommonCode.BLUE_SIDE.getCode(), liveGameStatDto.blueTeamStatDto.getDragon()[blueLength-1]);
        }
        int redLength = liveGameStatDto.getRedTeamStatDto().getDragon().length;
        if(redLength > this.redTeamStatDto.getDragon().length){
            dragon.put(CommonCode.RED_SIDE.getCode(), liveGameStatDto.redTeamStatDto.getDragon()[redLength-1]);
        }
        return dragon;
    }

    private Map<String, Integer> checkBaronEvent(LiveGameStatDto liveGameStatDto){
        Map<String, Integer> baron = new HashMap<>();
        if(liveGameStatDto.getBlueTeamStatDto().getBaron() > this.blueTeamStatDto.getBaron()){
            baron.put(CommonCode.BLUE_SIDE.getCode(), liveGameStatDto.getBlueTeamStatDto().getBaron() - this.blueTeamStatDto.getBaron());
        }
        if(liveGameStatDto.getRedTeamStatDto().getBaron() > this.redTeamStatDto.getBaron()){
            baron.put(CommonCode.RED_SIDE.getCode(), liveGameStatDto.getRedTeamStatDto().getBaron() - this.redTeamStatDto.getBaron());
        }
        return baron;
    }

    private Map<String, Integer> checkTowerEvent(LiveGameStatDto liveGameStatDto){
        Map<String, Integer> tower = new HashMap<>();
        if(liveGameStatDto.getBlueTeamStatDto().getTower() > this.blueTeamStatDto.getTower()){
            tower.put(CommonCode.BLUE_SIDE.getCode(), liveGameStatDto.getBlueTeamStatDto().getTower() - this.blueTeamStatDto.getTower());
        }
        if(liveGameStatDto.getRedTeamStatDto().getTower() > this.redTeamStatDto.getTower()){
            tower.put(CommonCode.RED_SIDE.getCode(), liveGameStatDto.getRedTeamStatDto().getTower() - this.redTeamStatDto.getTower());
        }
        return tower;
    }

    private Map<String, Integer> checkInhibitorEvent(LiveGameStatDto liveGameStatDto){
        Map<String, Integer> inhibitor = new HashMap<>();
        if(liveGameStatDto.getBlueTeamStatDto().getInhibitor() > this.blueTeamStatDto.getInhibitor()){
            inhibitor.put(CommonCode.BLUE_SIDE.getCode(), liveGameStatDto.getBlueTeamStatDto().getInhibitor() - this.blueTeamStatDto.getInhibitor());
        }
        if(liveGameStatDto.getRedTeamStatDto().getInhibitor() > this.redTeamStatDto.getInhibitor()){
            inhibitor.put(CommonCode.RED_SIDE.getCode(), liveGameStatDto.getRedTeamStatDto().getInhibitor() - this.redTeamStatDto.getInhibitor());
        }
        return inhibitor;
    }

    private Map<String, Integer> checkGoldDiff(LiveGameStatDto liveGameStatDto){
        Map<String, Integer> goldDiff = new HashMap<>();
        Integer diff = liveGameStatDto.getBlueTeamStatDto().getGold() - liveGameStatDto.getRedTeamStatDto().getGold();
        if(diff > 0){
            goldDiff.put(CommonCode.BLUE_SIDE.getCode(), diff);
        }else if(diff < 0){
            goldDiff.put(CommonCode.RED_SIDE.getCode(), diff * -1);
        }else{
            goldDiff.put("SAME", 0);
        }
        return goldDiff;
    }
}
