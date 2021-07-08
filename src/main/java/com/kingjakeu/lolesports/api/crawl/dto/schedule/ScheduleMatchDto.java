package com.kingjakeu.lolesports.api.crawl.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ScheduleMatchDto {
    private String id;
    private Map<String, Object> strategy;
    private ArrayList<ScheduleTeamDto> teams;
    private ArrayList<String> flags;
}
