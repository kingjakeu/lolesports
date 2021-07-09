package com.kingjakeu.lolesports.api.rune.dao;

import com.kingjakeu.lolesports.api.rune.domain.Rune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuneRepository extends JpaRepository<Rune, String> {
}
