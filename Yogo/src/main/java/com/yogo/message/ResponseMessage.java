package com.yogo.message;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseMessage {
    private int status;
    private String message;
    private Object result;

    public ResponseMessage(Object result) {
        this.status = HttpStatus.OK.value();
        this.message = "Successful";
        this.result = result;
    }

    public ResponseMessage(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.result = data;
    }

    public static ResponseEntity<?> success(Object result) {
        return ResponseEntity.ok(new ResponseMessage(result));
    }

    public static ResponseEntity<?> build(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(new ResponseMessage(status, message, data));
    }
}