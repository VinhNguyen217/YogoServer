package com.yogo.business.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.yogo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserSocket {
    private String userId;
    private SocketIOClient socketIOClient;
    private Status status;
}
