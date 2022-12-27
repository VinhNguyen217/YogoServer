package com.yogo.business.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {
    private String userName;
    private String content;

    private String target;

    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", message='" + content + '\'' +
                '}';
    }
}
