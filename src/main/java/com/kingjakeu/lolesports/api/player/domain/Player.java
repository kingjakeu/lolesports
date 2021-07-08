package com.kingjakeu.lolesports.api.player.domain;

import com.kingjakeu.lolesports.api.common.constant.LolRole;
import com.kingjakeu.lolesports.api.team.domain.Team;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PLAYER_INFO")
public class Player {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "SUMMONER_NAME", length = 50)
    private String summonerName;

    @Setter
    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "ENG_NAME", length = 50)
    private String englishName;

    @Setter
    @Column(name = "BIRTHDAY")
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANE_ROLE", length = 5)
    private LolRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Setter
    @Column(name = "NATIONALITY", length = 50)
    private String nationality;

    @Lob
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;
}
