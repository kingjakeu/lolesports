package com.kingjakeu.lolesports.api.crawl.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LolEsportDataDto<T> {
    private T data;
}
