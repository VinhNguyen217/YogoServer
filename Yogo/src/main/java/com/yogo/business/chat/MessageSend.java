package com.yogo.business.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
public class MessageSend {
    private String from;
    private String to;
    private String msg;
}
