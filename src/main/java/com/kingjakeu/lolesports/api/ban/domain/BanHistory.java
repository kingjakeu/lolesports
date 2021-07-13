package com.kingjakeu.lolesports.api.ban.domain;

import com.kingjakeu.lolesports.api.champion.domain.Champion;
import com.kingjakeu.lolesports.api.game.domain.Game;
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
@Table(name = "BAN_HISTORY")
public class BanHistory {

    @EmbeddedId
    private BanHistoryId banHistoryId;

    @MapsId("gameId")
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "BAN_CHAMP_ID")
    private Champion bannedChampion;

    @Column(name = "PATCH_VER", length = 20)
    private String patchVersion;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;
}
