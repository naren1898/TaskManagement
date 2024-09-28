package com.TaskManagement.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SignUpRequestDto {
    private String username;
    private String password;
}
