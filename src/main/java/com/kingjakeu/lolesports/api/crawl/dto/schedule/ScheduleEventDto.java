package com.kingjakeu.lolesports.api.crawl.dto.schedule;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import com.kingjakeu.lolesports.api.match.domain.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@ToString
public class ScheduleEventDto {
    private String blockName;
    private String startTime;
    private String state;
    private String type;
    private ScheduleLeagueDto league;
    private ScheduleMatchDto match;
    private ArrayList<ScheduleGameDto> games;

    public boolean isNotInProgress(){
        return !CommonCode.STATE_IN_PROGRESS.codeEqualsTo(this.state);
    }

    public boolean isStartDateBetween(LocalDate startDate, LocalDate endDate){
        LocalDate startLocalDate = ZonedDateTime.parse(this.startTime).toLocalDate();
        return (startLocalDate.isEqual(startDate) || startLocalDate.isAfter(startDate))
                && (startLocalDate.isEqual(endDate) || startLocalDate.isBefore(endDate));
    }

    public Match toMatchEntity(){
        return Match.builder()
                .id(this.match.getId())
                .blockName(this.blockName)
                .startTime(ZonedDateTime.parse(this.startTime).toLocalDateTime())
                .state(this.state)
                .build();
    }
}
