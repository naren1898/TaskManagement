package com.TaskManagement.Service;

import com.TaskManagement.Exception.InvalidCredentialException;
import com.TaskManagement.Exception.InvalidTokenException;
import com.TaskManagement.Exception.UserAlreadyExistException;
import com.TaskManagement.Exception.UserNotfoundException;
import com.TaskManagement.Model.Session;
import com.TaskManagement.Model.SessionStatus;
import com.TaskManagement.Model.User;
import com.TaskManagement.Repository.SessionRepository;
import com.TaskManagement.Repository.UserRepository;
import com.TaskManagement.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDTO signup(String username, String password) throws UserAlreadyExistException {
        Optional<User> useroptional = userRepository.findByUsername(username);
        if (!useroptional.isEmpty()) {
            throw new UserAlreadyExistException("User Already Exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return UserDTO.from(user);
    }

    public ResponseEntity<UserDTO> login(String username, String password) throws InvalidCredentialException,UserNotfoundException {
        Optional<User> useroptional = userRepository.findByUsername(username);
        if (useroptional.isEmpty()) {
            throw new UserNotfoundException("User Not found");
        }
        User user = useroptional.get();
        if (bCryptPasswordEncoder.encode(password).matches(user.getPassword())) {
            throw new InvalidCredentialException("Invalid Credentails");
        }
        UserDTO userdto = UserDTO.from(user);
        MacAlgorithm alg = Jwts.SIG.HS256; // HS256 algo added for JWT
        SecretKey key = alg.key().build(); // generating the secret key

        //start adding the claims
        Map<String, Object> jsonForJWT = new HashMap<>();
        jsonForJWT.put("userId", user.getId());
        jsonForJWT.put("roles", user.getRole());
        jsonForJWT.put("createdAt", new Date());
        jsonForJWT.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts.builder()
                .claims(jsonForJWT)  //added the claims
                .signWith(key, alg) // added the algo and key
                .compact(); //building the token
        Session session = new Session();
        session.setLoginAt(new Date());
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setExpiringAt(new Date());
        sessionRepository.save(session);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,token);
        ResponseEntity<UserDTO> responseEntity = new ResponseEntity<>(userdto, headers, HttpStatus.OK);
        return responseEntity;
    }
    public ResponseEntity<Void> logout(Long Id, String token) throws InvalidTokenException {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, Id);
        if (sessionOptional.isEmpty()){
            throw new InvalidTokenException();
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }
    public UserDTO getUserDetails(Long Id){
        Optional<User> userOptional = userRepository.findById(Id);
        if(userOptional.isEmpty()){
            return null;
        }
        return UserDTO.from(userOptional.get());
    }
}
