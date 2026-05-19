package org.example.reportsworkshopgft.blockedorder.infrastructure;

import org.example.reportsworkshopgft.blockedorder.infrastructure.persistence.BlockedOrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedOrderRepositoryJPA extends JpaRepository<BlockedOrderJPA, String> {}
