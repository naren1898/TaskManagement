package com.TaskManagement.Repository;

import com.TaskManagement.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Override
    Optional<Task> findById(Long task_id);
    List<Task> findAllByUser_id(Long userId);

    @Override
    List<Task> findAll();

     List<Task> findAllByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);
    @Query(value="SELECT id,created_at,description,due_date,priority,status,title,updated_at,user_id FROM Task WHERE (title LIKE %:title% OR description LIKE %:description%) and user_id = :userId", nativeQuery = true)
     List<Task> findAllByTitleContainingOrDescriptionContainingAndUser_id(String title, String description, Long userId, Pageable pageable);
}
