package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateDTO {
    private String username;

    private String password;

    private String email;
    @Override
    public String toString() {
        return "{\"username\": \""+ username + "\", \"password\": \"" + password + "\", \"email\": \"" + email + "\"}";
    }

}
