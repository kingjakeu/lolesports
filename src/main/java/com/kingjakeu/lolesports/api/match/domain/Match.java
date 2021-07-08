package com.kingjakeu.lolesports.api.match.domain;

import com.kingjakeu.lolesports.api.common.constant.CommonCode;
import com.kingjakeu.lolesports.api.team.domain.Team;
import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MATCH_INFO")
public class Match {
    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "BLOCK_NAME", length = 100)
    private String blockName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOURNAMENT_ID")
    private Tournament tournament;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_1_ID")
    private Team team1;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_2_ID")
    private Team team2;

    @Column(name = "START_TIME", columnDefinition = "datetime")
    private LocalDateTime startTime;

    @Column(name = "STATE", length = 20)
    private String state;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;

    public boolean startDateEqualsTo(LocalDate localDate){
        return this.startTime.toLocalDate().equals(localDate);
    }

    public boolean isCompleted(){
        return CommonCode.STATE_COMPLETED.codeEqualsTo(this.state);
    }
}

