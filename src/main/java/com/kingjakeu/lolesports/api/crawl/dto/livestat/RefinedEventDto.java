package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class RefinedEventDto {
    private Set<Integer> killPlayer;
    private Set<Integer> deathPlayer;
    private Set<Integer> assistPlayer;

    public RefinedEventDto(){
        this.killPlayer = new HashSet<>();
        this.deathPlayer = new HashSet<>();
        this.assistPlayer = new HashSet<>();
    }

    public boolean isEventHappened(){
        return !this.killPlayer.isEmpty() || !this.deathPlayer.isEmpty() || !this.assistPlayer.isEmpty();
    }

    public String getMessage(Map<Integer, String> participantMap){
        StringBuilder sb = new StringBuilder();
        if(!this.killPlayer.isEmpty()){
            sb.append("KILL: ");
            for (Integer id : killPlayer){
                sb.append(participantMap.get(id)).append(" ");
            }
            sb.append("\n");
        }
        if(!this.assistPlayer.isEmpty()){
            sb.append("ASSIST: ");
            for (Integer id : assistPlayer){
                sb.append(participantMap.get(id)).append(" ");
            }
            sb.append("\n");
        }
        if(!this.deathPlayer.isEmpty()){
            sb.append("DEATH: ");
            for (Integer id : deathPlayer){
                sb.append(participantMap.get(id)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
