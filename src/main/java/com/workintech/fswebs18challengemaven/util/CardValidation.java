package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CardValidation {

    public void checkColor(String color) {
        try {
            Color.valueOf(color.toUpperCase());
        } catch (Exception e) {
            throw new CardException("Color not found", HttpStatus.NOT_FOUND);
        }
    }

    public void checkType(String type) {
        try {
            Type.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new CardException("Type not found", HttpStatus.NOT_FOUND);
        }
    }

    public void validateCard(Card card) {
        if (card == null) {
            throw new CardException("Card is null", HttpStatus.BAD_REQUEST);
        }

        Type type = card.getType();
        Integer value = card.getValue();
        Color color = card.getColor();


        if (type != null && value != null) {
            throw new CardException("Card cannot have both type and value", HttpStatus.BAD_REQUEST);
        }


        if (type == null && value == null) {
            throw new CardException("Card must have either type or value", HttpStatus.BAD_REQUEST);
        }


        if (type == Type.JOKER) {
            if (value != null || color != null) {
                throw new CardException("JOKER card must have null value and null color", HttpStatus.BAD_REQUEST);
            }
            return;
        }


        if (color == null) {
            throw new CardException("Color is required for non-JOKER cards", HttpStatus.BAD_REQUEST);
        }


        if (value != null && value <= 0) {
            throw new CardException("Value must be positive", HttpStatus.BAD_REQUEST);
        }
    }
}
