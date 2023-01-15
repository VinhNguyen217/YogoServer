package com.yogo.business.socket;

import java.util.ArrayList;
import java.util.List;

public class SocketClientManage {

    public List<UserSocket> list;

    private static SocketClientManage instance = null;

    private SocketClientManage() {
        list = new ArrayList<>();
    }

    public static SocketClientManage getInstance() {
        if (instance == null) {
            instance = new SocketClientManage();
        }
        return instance;
    }
}
