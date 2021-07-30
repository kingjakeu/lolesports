package com.kingjakeu.lolesports.api.live.dto;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class LiveGameEventDto {
    private Map<String, String> dragon;
    private Map<String, Integer> baron;
    private Map<String, Integer> tower;
    private Map<String, Integer> inhibitor;
    private Map<String, Integer> goldDiff;

    private Set<String> blueKillPlayer;
    private Set<String> blueDeathPlayer;
    private Set<String> blueAssistPlayer;

    private Set<String> redKillPlayer;
    private Set<String> redDeathPlayer;
    private Set<String> redAssistPlayer;

    public LiveGameEventDto(){
        this.blueKillPlayer = new HashSet<>();
        this.blueDeathPlayer = new HashSet<>();
        this.blueAssistPlayer = new HashSet<>();
        this.redKillPlayer = new HashSet<>();
        this.redDeathPlayer = new HashSet<>();
        this.redAssistPlayer = new HashSet<>();
    }

    public void addBlueEventPlayer(String playerName, LivePlayerDiffKdaDto diffKdaDto){
        if(diffKdaDto.getKill() > 0){
            this.blueKillPlayer.add(playerName);
        }
        if(diffKdaDto.getAssist() > 0){
            this.blueAssistPlayer.add(playerName);
        }
        if(diffKdaDto.getDeath() > 0){
            this.blueDeathPlayer.add(playerName);
        }
    }

    public void addRedEventPlayer(String playerName, LivePlayerDiffKdaDto diffKdaDto){
        if(diffKdaDto.getKill() > 0){
            this.redKillPlayer.add(playerName);
        }
        if(diffKdaDto.getAssist() > 0){
            this.redAssistPlayer.add(playerName);
        }
        if(diffKdaDto.getDeath() > 0){
            this.redDeathPlayer.add(playerName);
        }
    }

    public String getMessage(){
        StringBuilder sb = new StringBuilder("\n");
        sb.append("[GOLD DIFFERENCE]\n");
        sb.append(this.extractMessage(this.goldDiff));
        sb.append("\n");

        if(!this.blueDeathPlayer.isEmpty() || !this.redDeathPlayer.isEmpty()){
            sb.append("[FIGHT EVENT]\n");
            if(!this.blueKillPlayer.isEmpty() || !this.blueDeathPlayer.isEmpty()){
                sb.append("\n").append(CommonCode.BLUE_SIDE.getCode()).append("\n");
                sb.append(fightMessage(this.blueKillPlayer, this.blueAssistPlayer, this.blueDeathPlayer));
            }
            if(!this.redKillPlayer.isEmpty() || !this.redDeathPlayer.isEmpty()){
                sb.append("\n").append(CommonCode.RED_SIDE.getCode()).append("\n");
                sb.append(fightMessage(this.redKillPlayer, this.redAssistPlayer, this.redDeathPlayer));
            }
            sb.append("\n");
        }

        if(!this.dragon.isEmpty()){
            sb.append("[DRAGON EVENT]\n");
            sb.append(this.extractMessage(this.dragon));
            sb.append("\n");
        }
        if(!this.baron.isEmpty()){
            sb.append("[BARON EVENT]\n");
            sb.append(this.extractMessage(this.baron));
            sb.append("\n");
        }
        if(!this.tower.isEmpty()){
            sb.append("[TOWER EVENT]\n");
            sb.append(this.extractMessage(this.tower));
            sb.append("\n");
        }
        if(!this.inhibitor.isEmpty()){
            sb.append("[INHIBITOR EVENT]\n");
            sb.append(this.extractMessage(this.inhibitor));
            sb.append("\n");
        }
        return sb.toString();
    }

    public StringBuilder fightMessage(Set<String> killPlayer,
                                      Set<String> assistPlayer,
                                      Set<String> deathPlayer){
        StringBuilder sb = new StringBuilder();
        if(!killPlayer.isEmpty()){
            sb.append("KILL: ");
            for (String player : killPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        if(!assistPlayer.isEmpty()){
            sb.append("ASSIST: ");
            for (String player  : assistPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        if(!deathPlayer.isEmpty()){
            sb.append("DEATH: ");
            for (String player : deathPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        return sb;
    }

    public StringBuilder extractMessage(Map<String, ?> map){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, ?> entry : map.entrySet()){
            sb.append(entry.getKey()).append(": ").append(entry.getValue())
                    .append("\n");
        }
        return sb;
    }
}
