package com.kingjakeu.lolesports.api.crawl.dto.champion;

import com.kingjakeu.lolesports.api.champion.domain.Champion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ChampionDataDto {
    private LinkedHashMap<String, ChampionDto> data;
    private String format;
    private String type;
    private String version;

    public List<Champion> toChampionEntities(){
        List<Champion> championList = new ArrayList<>();
        for (Map.Entry<String, ChampionDto> entry : data.entrySet()){
            championList.add(entry.getValue().toChampionEntity());
        }
        return championList;
    }
}
