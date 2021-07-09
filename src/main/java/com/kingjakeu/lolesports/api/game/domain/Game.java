package com.kingjakeu.lolesports.api.game.domain;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import com.kingjakeu.lolesports.api.match.domain.Match;
import com.kingjakeu.lolesports.api.team.domain.Team;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GAME_INFO")
public class Game{
    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    @Column(name = "GAME_SEQ", length = 2)
    private Integer sequence;

    @Column(name = "STATE", length = 20)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLUE_TEAM_ID")
    private Team blueTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RED_TEAM_ID")
    private Team redTeam;

    @Setter
    @Column(name = "PATCH_VER", length = 20)
    private String patchVersion;

    @Column(name = "START_DATETIME", columnDefinition = "datetime")
    private LocalDateTime startTime;

    @Column(name = "START_MILLIS", length = 10)
    private Long startMillis;

    @Column(name = "END_MILLIS", length = 10)
    private Long endMillis;

    @Setter
    @Lob
    @Column(name = "MATCH_HISTORY_URL")
    private String matchHistoryUrl;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;

    public boolean isMatchHistoryLinkEmpty(){
        return this.matchHistoryUrl == null;
    }

    public Team getTeamBySide(String side){
        if (CommonCode.BLUE_SIDE.codeEqualsTo(side)){
            return this.blueTeam;
        }else if(CommonCode.RED_SIDE.codeEqualsTo(side)){
            return this.redTeam;
        }else{
            return null;
        }
    }
}
