package com.kingjakeu.lolesports.api.tournament.dao;

import com.kingjakeu.lolesports.api.tournament.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, String> {
}
