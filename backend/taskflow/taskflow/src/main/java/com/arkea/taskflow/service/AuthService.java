package com.arkea.taskflow.service;

import com.arkea.taskflow.repository.UserRepository;

public interface AuthService {
    String loginAndGenerateToken(String username, String password);
    void createTestUserIfNotExist();

}
