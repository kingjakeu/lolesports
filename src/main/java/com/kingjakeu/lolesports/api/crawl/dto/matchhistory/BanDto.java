package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BanDto {
    private Long championId;
    private Integer pickTurn;
}