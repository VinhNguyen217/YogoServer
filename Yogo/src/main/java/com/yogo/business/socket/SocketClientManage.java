package com.yogo.business.socket;

import java.util.HashMap;
import java.util.UUID;

public class SocketClientManage {

    public HashMap<String, UUID> map;

    private static SocketClientManage instance = null;

    private SocketClientManage() {
        map = new HashMap<>();
    }

    public static SocketClientManage getInstance() {
        if (instance == null) {
            instance = new SocketClientManage();
        }
        return instance;
    }
}
