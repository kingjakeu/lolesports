package com.kingjakeu.lolesports.api.crawl.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class ScheduleGameDto {
    private String id;
    private ArrayList<String> vods;
}
