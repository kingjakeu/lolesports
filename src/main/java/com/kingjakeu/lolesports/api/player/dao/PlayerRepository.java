package com.kingjakeu.lolesports.api.player.dao;

import com.kingjakeu.lolesports.api.player.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findPlayerBySummonerName(String summonerName);
}
