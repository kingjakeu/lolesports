package com.kingjakeu.lolesports.api.game.domain;

import com.kingjakeu.lolesports.api.player.domain.Player;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PLYR_RUNE_HIST")
public class PlayerRuneHistory {

    @Setter
    @EmbeddedId
    private PlayerRuneHistoryId playerRuneHistoryId;

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

    @Column(name = "PRIMARY_STYLE")
    private String primaryRuneStyle;

    @Column(name = "SUB_STYLE")
    private String subRuneStyle;

    @Column(name = "RUNE_0")
    private String runePick0;

    @Column(name = "RUNE_1")
    private String runePick1;

    @Column(name = "RUNE_2")
    private String runePick2;

    @Column(name = "RUNE_3")
    private String runePick3;

    @Column(name = "RUNE_4")
    private String runePick4;

    @Column(name = "RUNE_5")
    private String runePick5;

    @Column(name = "STAT_0")
    private String statRune0;

    @Column(name = "STAT_1")
    private String statRune1;

    @Column(name = "STAT_2")
    private String statRune2;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;
}
