package com.kingjakeu.lolesports.api.game.domain;

import com.kingjakeu.lolesports.api.common.constant.LolSide;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class TeamGameSummaryId implements Serializable {
    @Column(name = "GAME_ID")
    private String gameId;

    @Enumerated(EnumType.STRING)
    @Column(name = "SIDE")
    private LolSide side;

    @Builder
    public TeamGameSummaryId(String gameId, LolSide side){
        this.gameId = gameId;
        this.side = side;
    }
}
