package com.kingjakeu.lolesports.api.game.domain;

import com.kingjakeu.lolesports.api.common.constant.LolSide;
import com.kingjakeu.lolesports.api.team.domain.Team;
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
@Table(name = "TEAM_GAME_SMRY")
public class TeamGameSummary {
    @EmbeddedId
    private TeamGameSummaryId teamGameSummaryId;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @Column(name = "WIN")
    private Boolean win;

    @Column(name = "FIST_BLOOD")
    private Boolean firstBlood;

    @Column(name = "FIST_TOWER")
    private Boolean firstTower;

    @Column(name = "FIST_INHIBIT")
    private Boolean firstInhibitor;

    @Column(name = "FIST_BARON")
    private Boolean firstBaron;

    @Column(name = "FIST_DRAGON")
    private Boolean firstDragon;

    @Column(name = "FIST_RIFTH")
    private Boolean firstRiftHerald;

    @Column(name = "TOWER_KILL")
    private Integer towerKill;

    @Column(name = "INHIBIT_KILL")
    private Integer inhibitorKill;

    @Column(name = "BARON_KILL")
    private Integer baronKill;

    @Column(name = "DRAGON_KILL")
    private Integer dragonKill;

    @Column(name = "RIFTH_KILL")
    private Integer riftHeraldKill;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;
}
