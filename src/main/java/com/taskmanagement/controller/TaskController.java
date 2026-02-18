package com.taskmanagement.controller;

import com.taskmanagement.dto.TaskRequestDTO;
import com.taskmanagement.dto.TaskResponseDTO;
import com.taskmanagement.model.Task;
import com.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-management/tasks/")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("createTask")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO body){
        return taskService.createTask(body);
    }

    @GetMapping("getAllTasks")
    public List<TaskResponseDTO> getAllTask(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "createdAt") String sortBy,
                                            @RequestParam(required = false ) String search) {
        return taskService.getAllTask(page,size,sortBy,search);
    }

    @GetMapping("getTaskById/{id}")
    public TaskResponseDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("updateTask/{id}")
    public TaskResponseDTO updateTask(@PathVariable UUID id, @RequestBody TaskRequestDTO task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("deleteTask/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}
