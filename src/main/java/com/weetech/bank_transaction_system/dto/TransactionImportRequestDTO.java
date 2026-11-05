package com.weetech.bank_transaction_system.dto;

import jakarta.validation.constraints.NotBlank;

public class TransactionImportRequestDTO {

    @NotBlank(message = "URL is required")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}