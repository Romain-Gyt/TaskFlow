package com.arkea.taskflow.dto;


import java.util.UUID;

// 💡 Ici, le projectId est obligatoire
public record CreateTaskRequest(
       String title,
        UUID projectId
) {}