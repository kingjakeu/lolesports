package com.kingjakeu.lolesports.api.game.dao;

import com.kingjakeu.lolesports.api.game.domain.PlayerGameSummary;
import com.kingjakeu.lolesports.api.game.domain.PlayerGameSummaryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerGameSummaryRepository extends JpaRepository<PlayerGameSummary, PlayerGameSummaryId> {
}
