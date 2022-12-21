package com.yogo.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.corundumstudio.socketio.SocketIOClient;

public class SocketManager {
    public HashMap<String, UUID> map;
    public ArrayList<SocketIOClient> socketClientList;
    private static SocketManager instance = null;

    private SocketManager() {
        map = new HashMap<String, UUID>(); // key : sessionId của user - value : socketId của client
        socketClientList = new ArrayList<>();
    }

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }
}
