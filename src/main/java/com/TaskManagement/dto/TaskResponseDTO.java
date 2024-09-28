package com.TaskManagement.dto;

import com.TaskManagement.Model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;
    private Integer priority;
    private Date due_date;
    private Date created_At;
    private Date updated_At;
}
