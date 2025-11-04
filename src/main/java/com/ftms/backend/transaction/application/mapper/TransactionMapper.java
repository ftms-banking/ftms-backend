package com.ftms.backend.transaction.application.mapper;

import com.ftms.backend.transaction.application.dto.TransactionResponse;
import com.ftms.backend.transaction.application.dto.TransactionPageResponse;
import com.ftms.backend.transaction.domain.entity.Transaction;
import com.ftms.backend.transaction.infrastructure.persistence.TransactionJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
    public class TransactionMapper {

        public TransactionResponse toResponse(Transaction tx) {
            var dto = new TransactionResponse();
            dto.setUuid(tx.getUuid());
            dto.setIdempotencyKey(tx.getIdempotencyKey());          // added mapping
            dto.setSourceAccountId(tx.getSourceAccountId());         // added mapping
            dto.setDestinationAccountId(tx.getDestinationAccountId());// added mapping
            dto.setAmount(tx.getAmount());
            dto.setCurrency(tx.getCurrency());
            dto.setDescription(tx.getDescription());
            dto.setStatus(tx.getStatus().name());
            dto.setType(tx.getType().name());
            dto.setCreatedAt(tx.getCreatedAt());
            dto.setCompletedAt(tx.getCompletedAt());
            return dto;
        }

        public Transaction toDomain(TransactionJpaEntity entity) {
            return new Transaction(
                    entity.getUuid(),
                    entity.getSourceAccountId(),
                    entity.getDestinationAccountId(),
                    entity.getAmount(),
                    entity.getCurrency(),
                    entity.getDescription(),
                    entity.getStatus(),
                    entity.getType(),
                    entity.getCreatedAt(),
                    entity.getCompletedAt(),
                    entity.getIdempotencyKey()   // <-- add this line
            );
        }



        public TransactionPageResponse toPageResponse(Page<Transaction> page) {
            var response = new TransactionPageResponse();
            response.setContent(page.map(this::toResponse).getContent());
            response.setPage(page.getNumber());
            response.setSize(page.getSize());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            return response;
        }
    }
