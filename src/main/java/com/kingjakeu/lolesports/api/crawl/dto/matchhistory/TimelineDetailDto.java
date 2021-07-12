package com.kingjakeu.lolesports.api.crawl.dto.matchhistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TimelineDetailDto {
    @JsonProperty(value = "0-10")
    private Double zeroToTen;

    @JsonProperty(value = "10-20")
    private Double tenToTwenty;

    @JsonProperty(value = "20-30")
    private Double twentyToThirty;

    @JsonProperty(value = "30-end")
    private Double thirtyToEnd;

}
