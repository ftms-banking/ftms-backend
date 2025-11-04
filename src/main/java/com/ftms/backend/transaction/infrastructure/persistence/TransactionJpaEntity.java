package com.ftms.backend.transaction.infrastructure.persistence;

import com.ftms.backend.transaction.domain.entity.TransactionStatus;
import com.ftms.backend.transaction.domain.entity.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class TransactionJpaEntity {

    @Id
    private UUID uuid;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    @Column(name = "source_account_id")
    private UUID sourceAccountId;

    @Column(name = "destination_account_id")
    private UUID destinationAccountId;

    private BigDecimal amount;
    private String currency;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private OffsetDateTime createdAt;
    private OffsetDateTime completedAt;

    // getters and setters
    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }

    public UUID getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(UUID sourceAccountId) { this.sourceAccountId = sourceAccountId; }

    public UUID getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(UUID destinationAccountId) { this.destinationAccountId = destinationAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }
}
