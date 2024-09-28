package com.TaskManagement.Service;

import com.TaskManagement.Mapper.TaskMapper;
import com.TaskManagement.Model.Task;
import com.TaskManagement.Model.User;
import com.TaskManagement.Repository.TaskRepository;
import com.TaskManagement.Repository.UserRepository;
import com.TaskManagement.dto.TaskListResponseDTO;
import com.TaskManagement.dto.TaskRequestDTO;
import com.TaskManagement.dto.TaskResponseDTO;
import com.TaskManagement.dto.UpdatedTaskRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Long userId) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCreated_At(new Date());
        task.setUpdated_At(new Date());
        task.setStatus(taskRequestDTO.getStatus());
        task.setDue_date(taskRequestDTO.getDue_date());
        task.setPriority(taskRequestDTO.getPriority());
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        task.setUser(user);
        Task savedtask = taskRepository.save(task);
        return TaskMapper.TasktoTaskResponseDTO(savedtask);
    }
    public TaskResponseDTO updateTask(Long id, UpdatedTaskRequestDTO updatedTaskRequestDTO) {
        Optional<Task> optionaltask = taskRepository.findById(id);
        if(optionaltask.isEmpty()){
            return null;
        }
        Task task = optionaltask.get();
        task.setTitle(updatedTaskRequestDTO.getTitle());
        task.setStatus(updatedTaskRequestDTO.getStatus());
        task.setDescription(updatedTaskRequestDTO.getDescription());
        task.setPriority(updatedTaskRequestDTO.getPriority());
        Task savedtask = taskRepository.save(task);
        return TaskMapper.TasktoTaskResponseDTO(savedtask);
    }
    public TaskListResponseDTO getAllTasks(Long userId) {
        List<Task> tasks = taskRepository.findAllByUser_id(userId);
        return TaskMapper.tasksDTOtaskListResponseDTO(tasks);
    }
    public boolean deleteTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Page<TaskResponseDTO> searchTasks(String keyword, int page, int size, Long userId) {

        Pageable pageable = PageRequest.of(page, size);
        List<Task> tasks = taskRepository.findAllByTitleContainingOrDescriptionContainingAndUser_id(keyword, keyword, userId, pageable);
        TaskListResponseDTO taskListResponseDTOS = TaskMapper.tasksDTOtaskListResponseDTO(tasks);
        Page<TaskResponseDTO> taskListResponseDTOSPage = new PageImpl<>(taskListResponseDTOS.getTasks());
        return taskListResponseDTOSPage;
    }
}
