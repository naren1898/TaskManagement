package com.TaskManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class TaskListResponseDTO {
    private List<TaskResponseDTO> tasks;

    public TaskListResponseDTO() {
        this.tasks = new ArrayList<>();
    }
}
