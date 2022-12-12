package com.yogo.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Status status;
    private String content;
    private String sender;
    private String receiver;

    public enum Status {
        CHAT,
        JOIN,
        LEAVE
    }
}
