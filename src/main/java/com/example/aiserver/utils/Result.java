package com.example.aiserver.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result ok() {
        return new Result(200, "success", null);
    }

    public static Result fail() {
        return new Result(500, "fail", null);
    }

    public static Result ok(String message) {
        return new Result(200, message, null);
    }

    public static Result fail(String message) {
        return new Result(500, message, null);
    }

    public static Result ok(String message, Object data) {
        return new Result(200, message, data);
    }

    public static Result fail(String message, Object data) {
        return new Result(500, message, data);
    }
}
