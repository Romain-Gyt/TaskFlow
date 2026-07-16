package com.arkea.taskflow.service.impl;

import com.arkea.taskflow.model.User;
import com.arkea.taskflow.repository.UserRepository;
import com.arkea.taskflow.security.JwtService;
import com.arkea.taskflow.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String loginAndGenerateToken(String username, String password) {
      User user = userRepository.findByEmail(username)
              .orElseThrow(() -> new RuntimeException("username not found"));

      if(!passwordEncoder.matches(password, user.getPassword())) {
          throw new RuntimeException("password not match");
      }

      return jwtService.generateToken(user.getEmail(),new HashMap<>());
    }

    @Override
    public void createTestUserIfNotExist() {
        if(userRepository.findByEmail("admin@arkea.com").isEmpty()) {
            String hashedPassword = passwordEncoder.encode("password123");
            User user = User.builder().email("admin@arkea.com").password(hashedPassword).build();
            userRepository.save(user);
        }
    }
}
