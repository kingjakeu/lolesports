package com.kingjakeu.lolesports.api.champion.dao;

import com.kingjakeu.lolesports.api.champion.domain.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, String> {
}
