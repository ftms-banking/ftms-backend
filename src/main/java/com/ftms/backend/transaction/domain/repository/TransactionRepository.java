package com.ftms.backend.transaction.domain.repository;

import com.ftms.backend.transaction.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface TransactionRepository {
    Page<Transaction> findByFilters(UUID accountId, String type, String status,
                                    OffsetDateTime fromDate, OffsetDateTime toDate, Pageable pageable);
}
