package com.example.demo.service;

import com.example.demo.domain.dto.AccountCreateDTO;
import com.example.demo.domain.event.AccountCreatedEvent;
import com.example.demo.domain.event.AccountDeletedEvent;
import com.example.demo.domain.event.AccountUpdatedEvent;
import com.example.demo.domain.model.account.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {
    private final AccountService accountService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic topic1;

    @EventListener
    @KafkaListener(topics = "test_topic")
    public void processAdd(@Payload String event) throws JsonProcessingException {
        log.info("event received: " + event);
        //kafkaTemplate.send("test_topic",0, event.getAggregateObject(),"added");
        ObjectMapper mapper = new ObjectMapper();
        accountService.createAccount(mapper.readValue(event, AccountCreateDTO.class));
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
