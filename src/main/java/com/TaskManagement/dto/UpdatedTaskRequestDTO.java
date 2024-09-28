package com.TaskManagement.dto;

import com.TaskManagement.Model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedTaskRequestDTO {
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;
    private Integer priority;
}
