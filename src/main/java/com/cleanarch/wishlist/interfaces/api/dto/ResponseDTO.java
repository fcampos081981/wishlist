package com.cleanarch.wishlist.interfaces.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private T data;
    private String message;
    private int statusCode;

    public ResponseDTO(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }
}
