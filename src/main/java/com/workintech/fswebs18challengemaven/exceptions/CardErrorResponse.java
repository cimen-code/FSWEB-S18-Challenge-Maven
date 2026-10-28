package com.workintech.fswebs18challengemaven.exceptions;

import lombok.Getter;

@Getter
public class CardErrorResponse {

    private final String message;

    public CardErrorResponse(String message) {
        this.message = message;
    }
}
