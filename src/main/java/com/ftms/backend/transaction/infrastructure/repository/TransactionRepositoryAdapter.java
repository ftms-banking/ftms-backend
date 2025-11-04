package com.ftms.backend.transaction.infrastructure.repository;

import com.ftms.backend.transaction.domain.entity.Transaction;
import com.ftms.backend.transaction.domain.repository.TransactionRepository;
import com.ftms.backend.transaction.application.mapper.TransactionMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final JpaTransactionRepository jpaRepo;
    private final TransactionMapper mapper;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaRepo, TransactionMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Page<Transaction> findByFilters(UUID accountId, String type, String status,
                                           OffsetDateTime fromDate, OffsetDateTime toDate, Pageable pageable) {
        var page = jpaRepo.findByFilters(accountId, type, status, fromDate, toDate, pageable);
        return page.map(mapper::toDomain);
    }
}
