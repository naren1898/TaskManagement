package com.TaskManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private String message;
    private int messageCode;
}
