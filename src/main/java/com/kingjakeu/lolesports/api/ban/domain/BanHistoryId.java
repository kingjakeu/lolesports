package com.kingjakeu.lolesports.api.ban.domain;

import com.kingjakeu.lolesports.api.common.constant.LolSide;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BanHistoryId implements Serializable {
    @Column(name = "GAME_ID")
    private String gameId;

    @Column(name = "BAN_TURN", length = 2)
    private Integer banTurn;

    @Enumerated(EnumType.STRING)
    @Column(name = "SIDE")
    private LolSide side;


    @Builder
    public BanHistoryId(String gameId, Integer banTurn, LolSide side){
        this.gameId = gameId;
        this.banTurn = banTurn;
        this.side = side;
    }
}
