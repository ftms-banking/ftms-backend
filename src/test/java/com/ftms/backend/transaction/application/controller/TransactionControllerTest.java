package com.ftms.backend.transaction.application.controller;
import com.ftms.backend.transaction.api.TransactionController;
import com.ftms.backend.transaction.application.dto.TransactionPageResponse;
import com.ftms.backend.transaction.application.dto.TransactionResponse;
import com.ftms.backend.transaction.application.usecase.GetTransactionsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false) // disables Spring Security filters
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTransactionsUseCase getTransactionsUseCase;

    @Test
    void testGetTransactions() throws Exception {
        // Create example transaction
        TransactionResponse mockTx = new TransactionResponse();
        UUID uuid = UUID.randomUUID();
        mockTx.setUuid(uuid);
        mockTx.setIdempotencyKey("string");
        mockTx.setSourceAccountId(uuid);
        mockTx.setDestinationAccountId(uuid);
        mockTx.setAmount(BigDecimal.ZERO);
        mockTx.setCurrency("string");
        mockTx.setDescription("string");
        mockTx.setStatus("PENDING");
        mockTx.setType("DEPOSIT");
        mockTx.setCreatedAt(OffsetDateTime.now());
        mockTx.setCompletedAt(OffsetDateTime.now());

        // Create page response
        TransactionPageResponse pageResponse = new TransactionPageResponse();
        pageResponse.setContent(List.of(mockTx));
        pageResponse.setPage(0);
        pageResponse.setSize(0);
        pageResponse.setTotalElements(0);
        pageResponse.setTotalPages(0);

        // Mock the use case call
        when(getTransactionsUseCase.execute(
                any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(pageResponse);

        // Perform GET request
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].description").value("string"))
                .andExpect(jsonPath("$.content[0].status").value("PENDING"))
                .andExpect(jsonPath("$.content[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$.content[0].amount").value(0));
    }
}

