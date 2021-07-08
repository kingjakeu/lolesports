package com.kingjakeu.lolesports.api.crawl.dto.schedule;

import com.kingjakeu.lolesports.api.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ScheduleTeamDto {
    private String code;
    private String image;
    private String name;
    private Map<String, Object> record;
    private Map<String, Object> result;

    public Team toTeamEntity(){
        return Team.builder()
                .code(this.code)
                .build();
    }
}
