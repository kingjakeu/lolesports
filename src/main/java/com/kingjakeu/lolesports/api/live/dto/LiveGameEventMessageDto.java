package com.kingjakeu.lolesports.api.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class LiveGameEventMessageDto {
    private List<String> killPlayer;
    private List<String> deathPlayer;
    private List<String> assistPlayer;

    public String getMessage(){
        StringBuilder sb = new StringBuilder();
        if(!this.killPlayer.isEmpty()){
            sb.append("KILL: ");
            for (String player : killPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        if(!this.assistPlayer.isEmpty()){
            sb.append("ASSIST: ");
            for (String player  : assistPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        if(!this.deathPlayer.isEmpty()){
            sb.append("DEATH: ");
            for (String player : deathPlayer){
                sb.append(player).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
