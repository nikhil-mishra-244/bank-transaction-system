
package com.weetech.bank_transaction_system.dto;

public class LoginResponseDTO {

    private String token;
    private String tokenType;
    private String message;

    public LoginResponseDTO(
            String token,
            String tokenType,
            String message
    ) {
        this.token = token;
        this.tokenType = tokenType;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
