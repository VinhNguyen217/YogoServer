package com.yogo.business.socket;

import java.util.HashMap;
import java.util.UUID;

public class SocketDriverManage {
    public HashMap<String, UUID> map;

    private static SocketDriverManage instance = null;

    private SocketDriverManage() {
        map = new HashMap<>();
    }

    public static SocketDriverManage getInstance() {
        if (instance == null) {
            instance = new SocketDriverManage();
        }
        return instance;
    }
}
