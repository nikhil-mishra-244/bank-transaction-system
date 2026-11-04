package com.weetech.bank_transaction_system.dto;

public class RegisterResponseDTO {

    private String message;
    private boolean success;

    public RegisterResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

}
