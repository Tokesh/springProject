package com.example.demo.controller;

import com.example.demo.domain.dto.AccountCreateDTO;
import com.example.demo.domain.dto.AccountUpdateDTO;
import com.example.demo.domain.event.AccountCreatedEvent;
import com.example.demo.domain.model.account.Account;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;
    @GetMapping("/accounts/")
    public List<Account> findAll() {
        return accountService.findAllAccounts();
    }
//    @PostMapping("/accounts/")
//    public Account createAccount(@RequestBody AccountCreateDTO accountCreateDTO) throws JsonProcessingException {
//        AccountCreatedEvent event = new AccountCreatedEvent();
//        event.setEmittedDate(LocalDateTime.now());
//        event.setAggregateObjectType("Account");
//        event.setAggregateObject(String.valueOf(accountCreateDTO.toString()));
//
//        eventPublisher.publishEvent(event);
//        System.out.println(event);
//        return accountService.createAccount(accountCreateDTO);
//    }
    @PostMapping("/accounts/")
    public void createAccount(@RequestBody AccountCreateDTO accountCreateDTO) throws JsonProcessingException {
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setEmittedDate(LocalDateTime.now());
        event.setAggregateObjectType("Account");
        event.setAggregateObject(String.valueOf(accountCreateDTO.toString()));
        eventPublisher.publishEvent(event);
    }
    @PutMapping("accounts/{id}/")
    public Account updateAccount(@PathVariable Integer id,@RequestBody AccountUpdateDTO accountUpdateDTO) throws JsonProcessingException {
        return accountService.updateAccount(id, accountUpdateDTO);
    }
    @DeleteMapping("accounts/{id}/")
    public Boolean deleteAccount(@PathVariable Integer id) throws JsonProcessingException {
        return accountService.deleteAccount(id);
    }
}
