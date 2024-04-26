package spring.testapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spring.testapp.dto.RegisterRequest;
import spring.testapp.model.User;

import java.time.Instant;

@Service
public class AuthService {

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setCreatedAt(Instant.now());
        user.setEnabled(false);
    }
}
