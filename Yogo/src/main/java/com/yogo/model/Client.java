package com.yogo.model;

import com.yogo.enums.Role;
import lombok.Data;

@Data
public class Client extends User {

    public Client() {
        super();
    }

    public Client(Integer id, String username, String email, String phone, String address, String password, Role role) {
        super(id, username, email, phone, address, password, role);
    }

}
