package com.cleanarch.wishlist.interfaces.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroResponseDTO {
    private String message;

    public ErroResponseDTO(String message) {
        this.message = message;
    }
}
