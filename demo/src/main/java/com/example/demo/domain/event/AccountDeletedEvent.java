package com.example.demo.domain.event;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AccountDeletedEvent {
    String aggregateObjectType;
    String aggregateObjectId;
    String messagePayload;
    LocalDateTime emittedDate;
}
