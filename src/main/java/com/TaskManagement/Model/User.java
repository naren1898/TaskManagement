package com.TaskManagement.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name="users_task")
@Getter
@Setter
public class User extends BaseModel{
    private String username;
    private String password;
    private Role role;
}
