package com.example.demo.domain.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountCreatedEvent {
    String aggregateObjectType;
    String aggregateObject;
    String messagePayload;
    LocalDateTime emittedDate;
}
