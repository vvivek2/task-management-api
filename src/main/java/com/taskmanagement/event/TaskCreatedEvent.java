package com.taskmanagement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskCreatedEvent {
    private UUID taskId;
    private String name;
    private LocalDateTime createdAt;
}
