package com.kingjakeu.lolesports.api.game.dao;

import com.kingjakeu.lolesports.api.game.domain.TeamGameSummary;
import com.kingjakeu.lolesports.api.game.domain.TeamGameSummaryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamGameSummaryRepository extends JpaRepository<TeamGameSummary, TeamGameSummaryId> {
}
