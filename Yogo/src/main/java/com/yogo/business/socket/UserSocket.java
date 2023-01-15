package com.yogo.business.socket;

import com.yogo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserSocket {
    private String userId;
    private UUID socketId;
    private Status status;
}
