package com.kingjakeu.lolesports.api.team.dao;

import com.kingjakeu.lolesports.api.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
    Team getTeamByCode(String code);
}
