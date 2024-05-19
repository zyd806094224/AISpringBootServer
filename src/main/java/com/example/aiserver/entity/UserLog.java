package com.example.aiserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {

    private Integer logId;
    private Integer userId;
    private String username;
    private Integer preLogId;
    private String question;
    private String answer;
    private LocalDateTime dateTime;
    private Long consumeTime;
}
