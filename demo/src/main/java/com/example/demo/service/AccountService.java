package com.example.demo.service;

import com.example.demo.domain.dto.AccountCreateDTO;
import com.example.demo.domain.dto.AccountUpdateDTO;
import com.example.demo.domain.event.AccountCreatedEvent;
import com.example.demo.domain.event.AccountDeletedEvent;
import com.example.demo.domain.event.AccountUpdatedEvent;
import com.example.demo.domain.model.account.Account;
import com.example.demo.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final ApplicationEventPublisher eventPublisher;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(AccountCreateDTO accountCreateDTO) throws JsonProcessingException {
        Account account = new Account();
        account.setEmail(accountCreateDTO.getEmail());
        account.setPassword(accountCreateDTO.getPassword());
        account.setUsername(accountCreateDTO.getUsername());
        Account savedAccount = accountRepository.save(account);

//        AccountCreatedEvent event = new AccountCreatedEvent();
//        event.setEmittedDate(LocalDateTime.now());
//        event.setAggregateObjectType("Account");
//        event.setAggregateObjectId(String.valueOf(savedAccount.getId()));
//        ObjectMapper mapper = new ObjectMapper();
//        event.setMessagePayload(mapper.writeValueAsString(savedAccount));

        //eventPublisher.publishEvent(event);
        return savedAccount;
    }
    public Account updateAccount(long id, AccountUpdateDTO accountUpdateDTO) throws JsonProcessingException{
        try{
            Account account = accountRepository.findById(id).get();
            account.setEmail(accountUpdateDTO.getEmail());
            account.setPassword(accountUpdateDTO.getPassword());
            account.setUsername(accountUpdateDTO.getUsername());
            Account savedAccount = accountRepository.save(account);

            AccountUpdatedEvent event = new AccountUpdatedEvent();
            event.setEmittedDate(LocalDateTime.now());
            event.setAggregateObjectType("Account");
            event.setAggregateObjectId(String.valueOf(savedAccount.getId()));

            ObjectMapper mapper = new ObjectMapper();
            event.setMessagePayload(mapper.writeValueAsString(savedAccount));
            //System.out.println(mapper.writeValueAsString(savedAccount));
            eventPublisher.publishEvent(event);
            return savedAccount;
        }catch (Exception ex) {
            return null;
        }
    }
    public Boolean deleteAccount(long id) throws JsonProcessingException{
        try{
            Account account = accountRepository.findById(id).get();
            accountRepository.deleteById(id);

            AccountDeletedEvent event = new AccountDeletedEvent();
            event.setEmittedDate(LocalDateTime.now());
            event.setAggregateObjectType("Account");
            event.setAggregateObjectId(String.valueOf(id));

            ObjectMapper mapper = new ObjectMapper();
            event.setMessagePayload(mapper.writeValueAsString("Account deleted"));
            //System.out.println(mapper.writeValueAsString(savedAccount));
            eventPublisher.publishEvent(event);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }
    @KafkaListener(topicPattern = "test_topic")
    public void listenGroup(String message){
        System.out.println("Received Message for group-2: " + message);
    }
//    @KafkaListener(topicPartitions
//            = @TopicPartition(topic = "test_topic", partitions = { "0" }))
//    public void listenGroup(@Payload String message,
//                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition){
//        System.out.println("Received Message for add: " + message + "from partition: " + partition);
//    }

}
