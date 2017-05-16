package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public class Task<T> {
    private T data;
    private String message;
}
