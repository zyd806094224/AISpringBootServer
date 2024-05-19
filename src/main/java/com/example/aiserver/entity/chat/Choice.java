package com.example.aiserver.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
   private String finish_reason;
   private Integer index;
   private Delta delta;
}
