package com.yogo.business.socket;

import java.util.ArrayList;
import java.util.List;

public class SocketDriverManage {
    public List<UserSocket> list;

    private static SocketDriverManage instance = null;

    private SocketDriverManage() {
        list = new ArrayList<>();
    }

    public static SocketDriverManage getInstance() {
        if (instance == null) {
            instance = new SocketDriverManage();
        }
        return instance;
    }
}
