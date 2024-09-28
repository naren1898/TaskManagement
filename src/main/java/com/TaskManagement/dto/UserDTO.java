package com.TaskManagement.dto;

import com.TaskManagement.Model.Role;
import com.TaskManagement.Model.User;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class UserDTO {
    private String username;
    private Role role;

    public static UserDTO from(User user){
        UserDTO userdto =new UserDTO();
        userdto.setUsername(user.getUsername());
        userdto.setRole(user.getRole());
        return userdto;
    }
}
