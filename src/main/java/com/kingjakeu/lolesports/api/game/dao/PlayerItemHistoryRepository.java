package com.kingjakeu.lolesports.api.game.dao;

import com.kingjakeu.lolesports.api.game.domain.PlayerItemHistory;
import com.kingjakeu.lolesports.api.game.domain.PlayerItemHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerItemHistoryRepository extends JpaRepository<PlayerItemHistory, PlayerItemHistoryId> {
}
