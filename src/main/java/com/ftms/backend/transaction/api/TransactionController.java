package com.ftms.backend.transaction.api;

import com.ftms.backend.transaction.application.dto.TransactionPageResponse;
import com.ftms.backend.transaction.application.usecase.GetTransactionsUseCase;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final GetTransactionsUseCase getTransactionsUseCase;

    public TransactionController(GetTransactionsUseCase getTransactionsUseCase) {
        this.getTransactionsUseCase = getTransactionsUseCase;
    }

    @GetMapping
    public TransactionPageResponse getTransactions(
            @RequestParam(required = false) UUID accountId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) OffsetDateTime fromDate,
            @RequestParam(required = false) OffsetDateTime toDate,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        return getTransactionsUseCase.execute(accountId, type, status, fromDate, toDate, pageable);
    }
}
