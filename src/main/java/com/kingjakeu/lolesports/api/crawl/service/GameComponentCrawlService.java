package com.kingjakeu.lolesports.api.crawl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingjakeu.lolesports.api.champion.dao.ChampionRepository;
import com.kingjakeu.lolesports.api.crawl.config.RiotGameComponentConfig;
import com.kingjakeu.lolesports.api.crawl.dto.champion.ChampionDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.item.ItemDataDto;
import com.kingjakeu.lolesports.api.crawl.dto.rune.ParentRuneDto;
import com.kingjakeu.lolesports.api.item.dao.ItemRepository;
import com.kingjakeu.lolesports.api.rune.dao.RuneRepository;
import com.kingjakeu.lolesports.util.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameComponentCrawlService {

    private final RiotGameComponentConfig riotGameComponentConfig;
    private final ChampionRepository championRepository;
    private final ItemRepository itemRepository;
    private final RuneRepository runeRepository;

    /**
     * Crawl Champion Data
     */
    public void crawlChampionDataInfo(){
        // Crawl Champion Data via Riot api
        ChampionDataDto dataDto = Crawler.doGetObject(this.riotGameComponentConfig.getChampionUrl(),  new TypeReference<>() {});

        // Save Champion Info
        this.championRepository.saveAll(dataDto.toChampionEntities());
    }

    /**
     * Crawl Item Data
     */
    public void crawlItemDataInfo(){
        // Crawl Item Data via Riot api
        System.out.println(this.riotGameComponentConfig.getItemUrl());
        ItemDataDto dataDto = Crawler.doGetObject(this.riotGameComponentConfig.getItemUrl(), new TypeReference<>() {});

        // Save Item Info
        this.itemRepository.saveAll(dataDto.toItemEntities());
    }

    /**
     * Crawl Rune Data
     */
    public void crawlRuneDataInfo(){
        // Crawl Rune Data via Riot APi
        ParentRuneDto[] dataDto = Crawler.doGetObject(this.riotGameComponentConfig.getRuneUrl(), new TypeReference<>() {});

        // Save Rune Info
        for(ParentRuneDto runeDto : dataDto){
            this.runeRepository.saveAll(runeDto.toRuneEntities());
        }
    }
}
