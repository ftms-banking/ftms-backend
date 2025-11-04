package com.ftms.backend.transaction.infrastructure.repository;

import com.ftms.backend.transaction.infrastructure.persistence.TransactionJpaEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionJpaEntity, UUID> {

    @Query("""
        SELECT t FROM TransactionJpaEntity t
        WHERE (:accountId IS NULL OR t.sourceAccountId = :accountId OR t.destinationAccountId = :accountId)
          AND (:type IS NULL OR t.type = :type)
          AND (:status IS NULL OR t.status = :status)
          AND (:fromDate IS NULL OR t.createdAt >= :fromDate)
          AND (:toDate IS NULL OR t.createdAt <= :toDate)
        """)
    Page<TransactionJpaEntity> findByFilters(UUID accountId, String type, String status,
                                             OffsetDateTime fromDate, OffsetDateTime toDate, Pageable pageable);
}
