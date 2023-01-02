package com.yogo.business.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message extends MessageChild {
    private String target;

    @Override
    public String toString() {
        return this.getUserName() + "-" + this.getContent() + "-" + this.getTarget();
    }
}
