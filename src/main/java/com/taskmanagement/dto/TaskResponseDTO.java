package com.taskmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import com.taskmanagement.model.taskStatus;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TaskResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private taskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
