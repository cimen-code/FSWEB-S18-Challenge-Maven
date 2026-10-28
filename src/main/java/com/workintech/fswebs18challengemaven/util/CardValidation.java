package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;

import org.springframework.http.HttpStatus;

public final class CardValidation {

    private CardValidation() {
    }

    public static void validateNew(Card card) {
        if (card == null) {
            badRequest("Card is required");
        }

        validateRules(card);

        if (card.getType() == null &&
            card.getValue() == null) {
            badRequest("Card must have either type or value");
        }

        if (card.getType() != Type.JOKER &&
            card.getColor() == null) {
            badRequest("Card color is required");
        }
    }

    public static void validateUpdate(Card card) {
        if (card == null || card.getId() == null) {
            badRequest("Card ID is required");
        }

        validateRules(card);
    }

    private static void validateRules(Card card) {
        if (card.getType() != null &&
            card.getValue() != null) {
            badRequest("Card cannot have both type and value");
        }

        if (card.getType() == Type.JOKER &&
            card.getColor() != null) {
            badRequest("Joker cannot have a color");
        }
    }

    private static void badRequest(String message) {
        throw new CardException(
            message, HttpStatus.BAD_REQUEST
        );
    }
}
