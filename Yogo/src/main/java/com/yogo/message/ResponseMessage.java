package com.yogo.message;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseMessage {
    private int errorCode;
    private String message;
    private Object result;

    public ResponseMessage(Object result) {
        this.errorCode = HttpStatus.OK.value();
        this.message = "Successful";
        this.result = result;
    }

    public static ResponseEntity<?> success(Object result) {
        return ResponseEntity.ok(new ResponseMessage(result));
    }
}
