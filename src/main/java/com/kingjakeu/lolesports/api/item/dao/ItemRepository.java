package com.kingjakeu.lolesports.api.item.dao;

import com.kingjakeu.lolesports.api.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
