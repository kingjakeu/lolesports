package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ParticipantPlayerDto {
    private String summonerName;
    private Long profileIcon;

    public String getRefinedSummonerName(){
        return this.summonerName.split(" ")[1];
    }
}
