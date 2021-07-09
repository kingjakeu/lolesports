package com.kingjakeu.lolesports.api.player.dao;

import com.kingjakeu.lolesports.api.player.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
