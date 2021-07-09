package com.kingjakeu.lolesports.api.champion.domain;

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
@Table(name = "CHAMPION")
public class Champion implements Persistable<String> {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "CHAMP_KEY", length = 50)
    private String champKey;

    @Column(name = "CHAMP_NAME", length = 50)
    private String name;

    @Column(name = "PATCH_VER", length = 20)
    private String patchVersion;

    @Lob
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "CREATE_DTM", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTM", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updateDateTime;

    @Override
    public boolean isNew() {
        return true;
    }
}
