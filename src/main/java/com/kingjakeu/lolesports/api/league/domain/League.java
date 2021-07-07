package com.kingjakeu.lolesports.api.league.domain;

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
@Table(name = "LEAGUE_INFO")
public class League {
    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "SLUG", nullable = false, length = 50)
    private String slug;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "REGION", nullable = false, length = 100)
    private String region;

    @Lob
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false,  columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;
}

