package com.example.demo.service;

import com.example.demo.domain.event.AccountCreatedEvent;
import com.example.demo.domain.event.AccountDeletedEvent;
import com.example.demo.domain.event.AccountUpdatedEvent;
import com.example.demo.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic topic1;

    @EventListener
    public void processAdd(AccountCreatedEvent event) {
        AccountUpdatedEvent event = new AccountUpdatedEvent();

        Account account;
        account.setEmail(accountUpdateDTO.getEmail());
        account.setPassword(accountUpdateDTO.getPassword());
        account.setUsername(accountUpdateDTO.getUsername());

        event.setEmittedDate(LocalDateTime.now());
        event.setAggregateObjectType("Account");
        event.setAggregateObjectId(String.valueOf(savedAccount.getId()));

        log.info("event received: " + event);
        kafkaTemplate.send("test_topic",0, event.getAggregateObjectId(),"added");
    }
    @EventListener
    public void processUpdate(AccountUpdatedEvent event) {
        log.info("event received: " + event);
        kafkaTemplate.send("test_topic",1, event.getAggregateObjectId(),"updated");
    }
    @EventListener
    public void processDelete(AccountDeletedEvent event) {
        log.info("event received: " + event);
        kafkaTemplate.send("test_topic",2, event.getAggregateObjectId(),"deleted");
    }

}
