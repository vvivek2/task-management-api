package com.taskmanagement.service;

import com.taskmanagement.dto.TaskRequestDTO;
import com.taskmanagement.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestdto);
    List<TaskResponseDTO> getAllTask(int page,
                                     int size,
                                     String sortBy,
                                     String search);

    TaskResponseDTO getTaskById(UUID id);
    TaskResponseDTO updateTask(UUID id, TaskRequestDTO task);

    void deleteTask(UUID id);

}
