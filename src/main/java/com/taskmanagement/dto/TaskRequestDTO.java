package com.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {

    @NotBlank(message = "Task name is required")
    @Size(max = 30)
    private String taskName;

    @Size(max = 100)
    private String taskDescription;
}
