package com.kingjakeu.lolesports.api.game.dao;

import com.kingjakeu.lolesports.api.game.domain.PlayerRuneHistory;
import com.kingjakeu.lolesports.api.game.domain.PlayerRuneHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRuneHistoryRepository extends JpaRepository<PlayerRuneHistory, PlayerRuneHistoryId> {
}
