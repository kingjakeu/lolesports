package com.kingjakeu.lolesports.api.crawl.dto.rune;

import com.kingjakeu.lolesports.api.rune.domain.Rune;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RuneDto {
    private Long id;
    private String key;
    private String icon;
    private String name;
    private String shortDesc;
    private String longDesc;

    public Rune toRuneEntity(){
        return Rune.builder()
                .id(this.id.toString())
                .name(this.name)
                .build();
    }
}
