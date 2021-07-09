package com.kingjakeu.lolesports.api.team.domain;

import com.kingjakeu.lolesports.api.league.domain.League;
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
@Table(name = "TEAM_INFO")
public class Team {
    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "CODE", nullable = false, length = 5)
    private String code;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "SLUG", nullable = false, length = 100)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID")
    private League league;

    @Lob
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;

    public boolean isUrlNameEquals(String urlName){
         return this.name
                 .replace(" ", "_")
                 .replace(".", "2e")
                 .equals(urlName);
    }
}
