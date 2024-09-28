package com.TaskManagement.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
public class Task extends BaseModel{
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;
    private Integer priority;
    private Date due_date;
    private Date created_At;
    private Date updated_At;
    @ManyToOne
    private User user;
}
