package com.kingjakeu.lolesports.api.crawl.dto.item;

import com.kingjakeu.lolesports.api.item.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ItemDataDto {
    private LinkedHashMap<String, Object> basic;
    private ArrayList<Object> groups;
    private ArrayList<Object> tree;
    private String type;
    private String version;
    private LinkedHashMap<String, ItemDto> data;

    public List<Item> toItemEntities(){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<String, ItemDto> entry : this.data.entrySet()){
            itemList.add(Item.builder()
                    .id(entry.getKey())
                    .name(entry.getValue().getName())
                    .patchVersion(this.version)
                    .build());
        }
        return itemList;
    }
}
