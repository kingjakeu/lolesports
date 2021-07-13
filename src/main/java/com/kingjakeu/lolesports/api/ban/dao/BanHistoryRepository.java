package com.kingjakeu.lolesports.api.ban.dao;

import com.kingjakeu.lolesports.api.ban.domain.BanHistory;
import com.kingjakeu.lolesports.api.ban.domain.BanHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanHistoryRepository extends JpaRepository<BanHistory, BanHistoryId> {
}
