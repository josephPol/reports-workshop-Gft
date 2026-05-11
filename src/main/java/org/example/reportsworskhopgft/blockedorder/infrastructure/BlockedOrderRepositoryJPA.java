package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.example.reportsworskhopgft.blockedorder.infrastructure.persistence.BlockedOrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlockedOrderRepositoryJPA extends JpaRepository<BlockedOrderJPA, String> {
}
