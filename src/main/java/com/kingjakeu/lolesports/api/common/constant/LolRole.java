package com.kingjakeu.lolesports.api.common.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum LolRole {
    TOP("Top Laner", "top"),
    JUG("Jungler", "jungle"),
    MID("Mid Laner", "mid"),
    BOT("Bot Laner", "bottom"),
    SUP("Support", "support"),
    COH("Coach", "coach"),
    NF("Not Found", "not-found")
    ;
    private final String fullName;
    private final String slugName;

    LolRole(String fullName, String slugName){
        this.fullName = fullName;
        this.slugName = slugName;
    }

    public static LolRole findByFullName(String fullName){
        for(LolRole lolRole : values()){
            if(lolRole.fullName.equals(fullName)){
                return lolRole;
            }
        }
        return NF;
    }

    public static LolRole findBySlugName(String slugName){
        for(LolRole lolRole : values()){
            if(lolRole.slugName.equals(slugName)){
                return lolRole;
            }
        }
        return NF;
    }

    public static LolRole findBySequence(int seq){
        switch (seq){
            case 0: case 5:
                return TOP;
            case 1: case  6:
                return JUG;
            case 2: case 7:
                return MID;
            case 3: case 8:
                return BOT;
            case 4: case 9:
                return SUP;
            default:
                return NF;
        }
    }

    public static List<LolRole> playerValues(){
        return Arrays.asList(LolRole.TOP, LolRole.JUG, LolRole.MID, LolRole.BOT, LolRole.SUP);
    }

}
