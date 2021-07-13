package com.kingjakeu.lolesports.api.game.domain;

import com.kingjakeu.lolesports.api.player.domain.Player;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PLYR_ITEM_HIST")
public class PlayerItemHistory {

    @Setter
    @EmbeddedId
    private PlayerItemHistoryId playerItemHistoryId;

    @Setter
    @MapsId("gameId")
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @Setter
    @MapsId("playerId")
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID")
    private Player player;

    @Column(name = "ITEM_0")
    private String item0;

    @Column(name = "ITEM_1")
    private String item1;

    @Column(name = "ITEM_2")
    private String item2;

    @Column(name = "ITEM_3")
    private String item3;

    @Column(name = "ITEM_4")
    private String item4;

    @Column(name = "ITEM_5")
    private String item5;

    @Column(name = "ITEM_6")
    private String item6;
}
