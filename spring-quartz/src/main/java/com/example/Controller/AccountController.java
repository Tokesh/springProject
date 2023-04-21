package com.example.Controller;

import com.example.domain.dto.AccountCreateDTO;
import com.example.domain.event.AccountCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final ApplicationEventPublisher eventPublisher;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @PostMapping("/create_account_vid_kafka")
    public AccountCreateDTO createAccount(@RequestBody AccountCreateDTO accountCreateDTO){
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setEmittedDate(LocalDateTime.now());
        event.setAggregateObjectType("Account");
        event.setAggregateObject(String.valueOf(accountCreateDTO.toString()));
        kafkaTemplate.send("test_topic", accountCreateDTO.toString());
        return accountCreateDTO;
    }

}
