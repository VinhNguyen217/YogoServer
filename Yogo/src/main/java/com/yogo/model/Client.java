package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Client extends User {

    public Client() {
        super();
    }

    public Client(Integer id, char type, String first_name, String last_name, String address, Integer gender,
                  String email) {
        super(id, type, first_name, last_name, address, gender, email);
    }

}
