package com.kingjakeu.lolesports.api.match.dao;

import com.kingjakeu.lolesports.api.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, String> {
}
