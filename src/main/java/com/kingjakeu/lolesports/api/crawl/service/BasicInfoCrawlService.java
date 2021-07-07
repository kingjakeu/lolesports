package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.crawl.config.RiotLolEsportsConfig;
import com.kingjakeu.lolesports.api.crawl.dto.LolEsportDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.league.LeagueDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicInfoCrawlService {

    private final RiotLolEsportsConfig esportsConfig;
    private final RiotEsportsComponent riotEsportsComponent;

    public void crawlLeagueInfo(){
        LolEsportDataDto<LeagueDataDto> resultDto = this.riotEsportsComponent.crawlLolEsportApi(
                "/getLeagues",
                this.riotEsportsComponent.createDefaultLolEsportParameters(),
                new TypeReference<>() {}
        );
        System.out.println(resultDto.toString());
    }
}
