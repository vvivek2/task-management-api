package com.taskmanagement.repository;

import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findByUserAndNameContainingIgnoreCase(
            User user,
            String name,
            Pageable pageable
    );
}
