package com.kingjakeu.lolesports.api.crawl.dto.rune;

import com.kingjakeu.lolesports.api.rune.domain.Rune;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RuneSlotDto {
    private RuneDto[] runes;

    public List<Rune> toRunEntities(){
        List<Rune> runeList = new ArrayList<>();
        for(RuneDto runeDto : runes){
            runeList.add(runeDto.toRuneEntity());
        }
        return runeList;
    }
}
