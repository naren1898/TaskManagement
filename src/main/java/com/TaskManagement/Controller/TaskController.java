package com.TaskManagement.Controller;

import com.TaskManagement.Exception.InvalidCredentialException;
import com.TaskManagement.Exception.UserAlreadyExistException;
import com.TaskManagement.Exception.UserNotfoundException;
import com.TaskManagement.Service.AuthService;
import com.TaskManagement.Service.TaskService;
import com.TaskManagement.Service.UserService;
import com.TaskManagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TaskController {
    private TaskService taskService;

    private UserService userService;
    private AuthService authService;

    public TaskController(TaskService taskService, UserService userService, AuthService authService) {
        this.taskService = taskService;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/tasks")
    public ResponseEntity createTask(@RequestBody TaskRequestDTO taskRequestDTO,@RequestHeader("token") String token) throws Exception {
        authService.validate(token);
        Long userId = authService.tokenEncoder(token);
        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO, userId);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody UpdatedTaskRequestDTO updatedTaskRequestDTO,@RequestHeader("token") String token) throws Exception {
        authService.validate(token);
        Long userId = authService.tokenEncoder(token);
        TaskResponseDTO responseDTO = taskService.updateTask(id,updatedTaskRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/tasks")
    public ResponseEntity getTasks(@RequestHeader("token") String token) throws Exception {
        authService.validate(token);
        Long userId = authService.tokenEncoder(token);
        TaskListResponseDTO response = taskService.getAllTasks(userId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id,@RequestHeader("token") String token) throws Exception {
        authService.validate(token);
        Long userId = authService.tokenEncoder(token);
        boolean isDeleted = taskService.deleteTaskById(id);
        if (isDeleted) return ResponseEntity.ok("Task deleted successfully.");
        else return ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public Page<TaskResponseDTO> searchTasks(@RequestParam("keyword") String keyword,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,@RequestHeader("token") String token) throws Exception {
        authService.validate(token);
        Long userId = authService.tokenEncoder(token);
        return taskService.searchTasks(keyword, page, size,userId);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistException {
        UserDTO userdto = userService.signup(signUpRequestDto.getUsername(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(userdto, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidCredentialException, UserNotfoundException {
        return userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword());
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("token") String token) throws Exception {
        Long userId = authService.tokenEncoder(token);
        return userService.logout(userId,token);
    }
}
