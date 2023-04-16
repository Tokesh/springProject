package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AccountUpdateDTO {
        private String username;
        private String password;
        private String email;

}
