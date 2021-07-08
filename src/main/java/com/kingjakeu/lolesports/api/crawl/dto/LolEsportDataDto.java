package com.kingjakeu.lolesports.api.crawl.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LolEsportDataDto<T> {
    private T data;
}
