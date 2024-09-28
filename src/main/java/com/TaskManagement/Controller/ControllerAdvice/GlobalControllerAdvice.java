package com.TaskManagement.Controller.ControllerAdvice;

import com.TaskManagement.Exception.InvalidCredentialException;
import com.TaskManagement.Exception.InvalidTokenException;
import com.TaskManagement.Exception.UserAlreadyExistException;
import com.TaskManagement.Exception.UserNotfoundException;
import com.TaskManagement.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(value = InvalidCredentialException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setMessageCode(404);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTokenException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setMessageCode(403);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setMessageCode(404);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = UserNotfoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotfoundException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setMessageCode(404);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
