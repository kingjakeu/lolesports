package com.kingjakeu.lolesports.api.live.dto;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class LiveGameEventDto {
    private Set<String> killPlayer;
    private Set<String> deathPlayer;
    private Set<String> assistPlayer;

    public LiveGameEventDto(){
        this.killPlayer = new HashSet<>();
        this.deathPlayer = new HashSet<>();
        this.assistPlayer = new HashSet<>();
    }

    public void addEventPlayer(String playerName, LivePlayerDiffKdaDto diffKdaDto){
        if(diffKdaDto.getKill() > 0){
            this.killPlayer.add(playerName);
        }
        if(diffKdaDto.getAssist() > 0){
            this.assistPlayer.add(playerName);
        }
        if(diffKdaDto.getDeath() > 0){
            this.deathPlayer.add(playerName);
        }
    }

    public void addAll(LiveGameEventDto liveGameEventDto){
        this.killPlayer.addAll(liveGameEventDto.getKillPlayer());
        this.assistPlayer.addAll(liveGameEventDto.getAssistPlayer());
        this.deathPlayer.addAll(liveGameEventDto.getDeathPlayer());
    }
}
