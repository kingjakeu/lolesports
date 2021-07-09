package com.kingjakeu.lolesports.api.crawl.dto.champion;

import com.kingjakeu.lolesports.api.champion.domain.Champion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Getter
@NoArgsConstructor
public class ChampionDto {
    private String id;
    private String key;
    private String name;
    private String title;
    private String partype;
    private String version;
    private String blurb;
    private LinkedHashMap<String, Object> image;
    private LinkedHashMap<String, Object> stats;
    private LinkedHashMap<String, Object> info;
    private String[] tags;

    public Champion toChampionEntity(){
        return Champion.builder()
                .id(this.key)
                .name(this.name)
                .champKey(this.id)
                .patchVersion(this.version)
                .build();
    }
}
