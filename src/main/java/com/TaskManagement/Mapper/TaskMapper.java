package com.TaskManagement.Mapper;

import com.TaskManagement.Model.Task;
import com.TaskManagement.dto.TaskListResponseDTO;
import com.TaskManagement.dto.TaskResponseDTO;

import java.util.List;

public class TaskMapper {
    public static TaskResponseDTO TasktoTaskResponseDTO(Task task){
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setStatus(task.getStatus());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setPriority(task.getPriority());
        taskResponseDTO.setDue_date(task.getDue_date());
        taskResponseDTO.setCreated_At(task.getCreated_At());
        taskResponseDTO.setUpdated_At(task.getUpdated_At());
        taskResponseDTO.setDue_date(task.getDue_date());
        return taskResponseDTO;
    }
    public static TaskListResponseDTO tasksDTOtaskListResponseDTO(List<Task> tasks){
        TaskListResponseDTO taskListResponseDTO = new TaskListResponseDTO();
        for(Task t : tasks){
            taskListResponseDTO.getTasks().add(TasktoTaskResponseDTO(t));
        }
        return taskListResponseDTO;
    }
}
