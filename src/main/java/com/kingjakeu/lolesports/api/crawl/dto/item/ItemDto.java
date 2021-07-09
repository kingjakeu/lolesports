package com.kingjakeu.lolesports.api.crawl.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Getter
@NoArgsConstructor
public class ItemDto {
    private String colloq;
    private Integer depth;
    private String description;
    private String[] from;
    private String name;
    private String plaintext;
    private Boolean consumeOnFull;
    private Boolean consumed;
    private Boolean inStore;
    private Boolean hideFromAll;
    private Integer stacks;
    private LinkedHashMap<String, Object> effect;
    private LinkedHashMap<String, Object> gold;
    private LinkedHashMap<String, Object> image;
    private String[] into;
    private LinkedHashMap<String, Object> maps;
    private LinkedHashMap<String, Object> stats;
    private String[] tags;
    private Long specialRecipe;
    private String requiredChampion;
    private String requiredAlly;
}
