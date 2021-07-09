package com.kingjakeu.lolesports.api.crawl.dto.game;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameVodDto {
    private String id;
    private String parameter;
    private String locale;
    private String provider;
    private Long offset;
    private String firstFrameTime;
    private Long startMillis;
    private Long endMillis;
}
