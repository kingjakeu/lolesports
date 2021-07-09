package com.kingjakeu.lolesports.api.game.dao;

import com.kingjakeu.lolesports.api.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String> {
    @Query("SELECT g FROM Game g JOIN FETCH g.blueTeam bt JOIN FETCH g.redTeam rt JOIN FETCH g.match m JOIN FETCH m.tournament t JOIN FETCH t.league l WHERE g.id = (:id)")
    Optional<Game> eagerFindGameById(String id);
}
