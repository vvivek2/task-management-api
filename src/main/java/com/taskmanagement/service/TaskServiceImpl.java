package com.taskmanagement.service;

import com.taskmanagement.dto.TaskRequestDTO;
import com.taskmanagement.dto.TaskResponseDTO;
import com.taskmanagement.event.TaskCreatedEvent;
import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import com.taskmanagement.model.taskStatus;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;


    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO){
        User currentUser = getCurrentUser();
        Task task = new Task();
        task.setName(taskRequestDTO.getTaskName());
        task.setDescription(taskRequestDTO.getTaskDescription());
        task.setStatus(taskStatus.NOT_STARTED);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUser(currentUser);
        return mapToResponse(repository.save(task));
    }

    @Cacheable(value = "tasks", key = "#page + '-' + #size + '-' + #search")
    @Override
    public List<TaskResponseDTO> getAllTask(int page,int size, String sortBy, String search) {

        if (size < 1) size = 5;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        User currentUser = getCurrentUser();
        Page<Task> pageTask;
        if(search != null && !search.isBlank()){
            pageTask = repository.findByUserAndNameContainingIgnoreCase(currentUser,search,pageable);
        }else{
            pageTask = repository
                    .findByUser(currentUser, pageable);
        }
        return pageTask.getContent().stream().map(this::mapToResponse).toList();
    }

    @Override
    public TaskResponseDTO getTaskById(UUID id) {
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return mapToResponse(task);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO updatedTask) {
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        User currentUser = getCurrentUser();
        if(!task.getUser().getId().equals(currentUser.getId())){
            throw new RuntimeException(currentUser.getUsername() +" is not authorized to modify task");
        }
        task.setName(updatedTask.getTaskName());
        task.setDescription(updatedTask.getTaskDescription());
        task.setStatus(taskStatus.NOT_STARTED);
        task.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(task));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public void deleteTask(UUID id) {
        Task task = repository.findById(id).orElseThrow(()-> new RuntimeException("task not found"));
        User currentUser = getCurrentUser();
        if(!task.getId().equals(currentUser.getId())){
            throw new RuntimeException(currentUser.getUsername() +" is not authorized to delete task");
        }
        repository.deleteById(id);
    }

    private TaskResponseDTO mapToResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
    }
}
