package com.yogo.model;

import com.yogo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Driver extends User {

    public Driver() {
        super();
    }

    public Driver(Integer id, char type, String first_name, String last_name, String address, Gender gender,
                  String email) {
        super(id, type, first_name, last_name, address, gender, email);
    }
}
