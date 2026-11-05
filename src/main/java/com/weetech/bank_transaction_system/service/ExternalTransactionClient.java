package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.ExternalTransactionDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ExternalTransactionClient {

    private final RestClient restClient;

    public ExternalTransactionClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public List<ExternalTransactionDTO> fetchTransactions(String url) {

        ExternalTransactionDTO[] response = restClient.get()
                .uri(url)
                .retrieve()
                .body(ExternalTransactionDTO[].class);

        if (response == null) {
            return List.of();
        }

        return List.of(response);
    }
}