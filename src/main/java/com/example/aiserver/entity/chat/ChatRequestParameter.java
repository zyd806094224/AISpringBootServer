package com.example.aiserver.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestParameter {
//    private String model = "gpt-3.5-turbo";
    private String model = "moonshot-v1-8k";  //月之暗面 模型

    private List<ChatMessage> messages = new ArrayList<>();

    private boolean stream = true;

    public void addMessages(ChatMessage message) {
        this.messages.add(message);
    }
}
