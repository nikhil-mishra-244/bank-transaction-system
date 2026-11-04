package com.weetech.bank_transaction_system.dto;

public class ProfileUpdateResponseDTO {

    private String message;
    private String name;

    public ProfileUpdateResponseDTO(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}