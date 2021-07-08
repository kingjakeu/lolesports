package com.kingjakeu.lolesports.api.league.dao;

import com.kingjakeu.lolesports.api.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, String> {
}
