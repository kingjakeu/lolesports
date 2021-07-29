package com.kingjakeu.lolesports.api.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveTeamStatDto implements Serializable {
    private Integer kill;
    private Integer gold;
    private Integer tower;
    private Integer inhibitor;
    private Integer baron;
    private String[] dragon;
}
