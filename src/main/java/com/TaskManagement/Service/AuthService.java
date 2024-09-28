package com.TaskManagement.Service;

import com.TaskManagement.Exception.InvalidTokenException;
import com.TaskManagement.Model.Session;
import com.TaskManagement.Model.SessionStatus;
import com.TaskManagement.Repository.SessionRepository;
import com.TaskManagement.dto.JwtPayloadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {
    private SessionRepository sessionRepository;
    public AuthService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void validate(String token) throws Exception {
        Long userId = tokenEncoder(token);
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (optionalSession.isEmpty()) { //|| optionalSession.get().getSessionStatus().equals(SessionStatus.ENDED)
            throw new InvalidTokenException("Invalid token");
        }
        Session session = optionalSession.get();
        if (!(session.getSessionStatus()==SessionStatus.ACTIVE)) {
            throw new InvalidTokenException("Token Expired");
        }
    }

    public Long tokenEncoder(String token) throws Exception {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(header);
        System.out.println(payload);
        ObjectMapper mapper = new ObjectMapper();
        JwtPayloadDTO jwtPayload = mapper.readValue(payload, JwtPayloadDTO.class);
        Long userId = jwtPayload.getUserId();
        return userId;
    }
}
