package com.kingjakeu.lolesports.api.game.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class PlayerGameSummaryId implements Serializable {
    @Setter
    @Column(name = "GAME_ID")
    private String gameId;

    @Setter
    @Column(name = "PLAYER_ID")
    private String playerId;

    @Builder
    public PlayerGameSummaryId (String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
}
