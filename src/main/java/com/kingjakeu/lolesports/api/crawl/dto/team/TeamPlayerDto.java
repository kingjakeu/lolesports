package com.kingjakeu.lolesports.api.crawl.dto.team;

import com.kingjakeu.lolesports.api.common.constant.LolRole;
import com.kingjakeu.lolesports.api.player.domain.Player;
import com.kingjakeu.lolesports.api.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamPlayerDto {
    private String id;
    private String summonerName;
    private String firstName;
    private String lastName;
    private String image;
    private String role;

    public Player toPlayerEntity(Team team){
        return Player.builder()
                .id(this.id)
                .summonerName(this.summonerName)
                .englishName(this.firstName.stripTrailing() + " " + this.lastName.stripTrailing())
                .team(team)
                .role(LolRole.findBySlugName(this.role))
                .imageUrl(this.image)
                .build();
    }
}
